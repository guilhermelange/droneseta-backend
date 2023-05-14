package com.udesc.droneseta.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.udesc.droneseta.model.dto.OrderDTO;
import com.udesc.droneseta.model.dto.OrderStatusDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import com.udesc.droneseta.repository.CustomerRepository;
import com.udesc.droneseta.service.OrderReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.Delivery;
import com.udesc.droneseta.model.OrderItem;
import com.udesc.droneseta.model.Product;
import com.udesc.droneseta.model.dto.OrderItemDTO;
import com.udesc.droneseta.model.enumerator.DeliveryStatus;
import com.udesc.droneseta.repository.DeliveryRepository;
import com.udesc.droneseta.repository.OrderItemRepository;
import com.udesc.droneseta.repository.OrderRepository;
import com.udesc.droneseta.repository.ProductRepository;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private ProductRepository productRepository;

        @Autowired
        private DeliveryRepository deliveryRepository;

	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody OrderDTO order) throws Exception {
		Optional<Customer> customer = customerRepository.findById(order.getCustomer_id());

		if (customer.isEmpty()) {
			throw new ApplicationException("Invalid Customer ID", HttpStatus.NOT_FOUND);
		}

		Order orderSave = new Order();
		orderSave.setPrice(order.getPrice());
		orderSave.setCustomer(customer.get());
		orderSave.setStatus(OrderStatus.PENDENTE);
		if (order.getStatus() != null)
			orderSave.setStatus(order.getStatus());
		Order savedOrder = repository.save(orderSave);

                for (OrderItemDTO o : order.getItems()) {
                    Optional<Product> op = productRepository.findById(o.getProduct_id());
                    Product prod = op.get();

                    OrderItem orderItemSave = new OrderItem();
                    orderItemSave.setOrder(savedOrder);
                    orderItemSave.setQuantity(o.getQuantity());
                    orderItemSave.setProduct(prod);
                    orderItemSave.setPrice(o.getPrice());

                    orderItemRepository.save(orderItemSave);

                    // Atualiza o estoque
                    prod.setStock(prod.getStock() - o.getQuantity());
                    productRepository.save(prod);
                }

		return ResponseEntity.ok().body(savedOrder);
	}

	@GetMapping("")
	public ResponseEntity<?> findAll(@RequestParam(value = "status", defaultValue = "") String status){
		if (status.isEmpty()) {
			return ResponseEntity.ok().body(repository.findAll());
		} else {
			List<String> statusStringSearch = Arrays.stream(status.split(",")).toList();
			List<OrderStatus> statusSearch = statusStringSearch.stream().map(item -> (OrderStatus.valueOf(item))).collect(Collectors.toList());
			return ResponseEntity.ok().body(repository.findAllOrderFilter(statusSearch));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findUnique(@PathVariable Integer id) throws Exception {
            Optional<Order> order = repository.findById(id);
            if (order.isEmpty()) {
                throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok().body(order.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody Order order) throws Exception {
            Optional<Order> findOrder = repository.findById(id);

            if (findOrder.isEmpty()) {
                    throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
            }

            Order savedOrder = repository.save(order);

            return ResponseEntity.ok().body(savedOrder);
	}

	@PatchMapping("/status/{id}")
	public ResponseEntity<?> updateStatus(@PathVariable Integer id, @Valid @RequestBody OrderStatusDTO order) throws Exception {
		Optional<Order> findOrder = repository.findById(id);

		if (findOrder.isEmpty()) {
			throw new ApplicationException("ID não localizado", HttpStatus.NOT_FOUND);
		}

		Order currentOrder = findOrder.get();
		currentOrder.setStatus(order.getStatus());

                if (currentOrder.getStatus().getKey() == OrderStatus.CONFIRMADO.getKey()) {
                    LocalDateTime atual = LocalDateTime.now();

                    for (OrderItem oi : currentOrder.getItems()) {
                        int qtd = oi.getQuantity();

                        while (qtd > 0) {
                            Optional<Delivery> last = deliveryRepository.findFirstByOrderByIdDesc();
                            Delivery d = new Delivery();
                            Boolean novaViagem = last.isEmpty() || last.get().getQuantity() == 10
                                || last.get().getDateTime().isBefore(atual);

                            if (!novaViagem) {
                                d = last.get();
                            }

                            int restante = 10 - d.getQuantity();
                            int enviar = qtd;

                            if (enviar > restante) {
                                enviar = restante;
                            }

                            qtd -= enviar;

                            if (novaViagem) {
                                // Se nao tinha entregas utiliza a data atual
                                if (last.isEmpty()) {
                                    d.setDateTime(atual);
                                    d.setStatus(DeliveryStatus.TRANSITO);

                                    currentOrder.setStatus(OrderStatus.TRANSITO);
                                } else {
                                    LocalDateTime termino = last.get().getDateTime().plusHours(1);

                                    // Se a última entrega já terminou, utiliza a data atual
                                    if (termino.isBefore(atual)) {
                                        d.setDateTime(atual);
                                        d.setStatus(DeliveryStatus.TRANSITO);

                                        currentOrder.setStatus(OrderStatus.TRANSITO);
                                    } else {
                                        // Se a última entrega não terminou então a próxima sairá assim que acabar a última
                                        d.setDateTime(termino);
                                        d.setStatus(DeliveryStatus.AGUARDANDO);
                                    }
                                }

                                d.setQuantity(enviar);
                            } else {
                                d.setQuantity(d.getQuantity() + enviar);
                            }

                            deliveryRepository.save(d);

                            currentOrder.setDelivery(d.getDateTime().plusHours(1));
                        }
                    }
                } else if (currentOrder.getStatus().getKey() == OrderStatus.CANCELADO.getKey()) {
                    for (OrderItem oi : currentOrder.getItems()) {
                        Optional<Product> op = productRepository.findById(oi.getProduct().getId());
                        Product prod = op.get();

                        // Atualiza o estoque
                        prod.setStock(prod.getStock() + oi.getQuantity());
                        productRepository.save(prod);
                    }
                }

		Order savedOrder = repository.save(currentOrder);

		return ResponseEntity.ok().body(savedOrder);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAll(@PathVariable Integer id) throws Exception {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<?> findByCustomer(@PathVariable Integer id) throws Exception {
                updateStatus();

		Customer customer = new Customer();
		customer.setId(id);
		return ResponseEntity.ok().body(repository.findByCustomer(customer));
	}

	@GetMapping(value = "/report")
	public ResponseEntity<?> generateReport() throws Exception {
		OrderReportService service = new OrderReportService(repository);
		String filename = service.generateReport();
		File file = new File(filename);

		byte[] bytes = Files.readAllBytes(file.toPath());
		ByteArrayResource resource = new ByteArrayResource(bytes);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=OrderReport.xlsx");
		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(bytes.length)
				.contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
				.body(resource);
	}

        private void updateStatus() {
            List<OrderStatus> status = new ArrayList<>();
            status.add(OrderStatus.TRANSITO);
            status.add(OrderStatus.CONFIRMADO);

            LocalDateTime atual = LocalDateTime.now();

            for (Order o : repository.findAllByStatusIn(status)) {
                if (o.getDelivery().isBefore(atual)) {
                    o.setStatus(OrderStatus.ENTREGUE);
                } else if (o.getDelivery().minusHours(1).isBefore(atual)) {
                    o.setStatus(OrderStatus.TRANSITO);
                }

                repository.save(o);
            }
        }

}
