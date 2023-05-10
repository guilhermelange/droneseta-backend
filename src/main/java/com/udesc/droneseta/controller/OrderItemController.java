package com.udesc.droneseta.controller;

import com.udesc.droneseta.model.Product;
import com.udesc.droneseta.model.dto.BestSellerDTO;
import com.udesc.droneseta.repository.OrderItemRepository;
import com.udesc.droneseta.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

	@Autowired
	private OrderItemRepository repository;

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("")
	public ResponseEntity<?> findAll(){
                return ResponseEntity.ok().body(repository.findAll());
	}

        @GetMapping("/{limit}")
	public ResponseEntity<?> findBestSeller(@PathVariable Integer limit) throws Exception {
                List<BestSellerDTO> orderItem = repository.findBestN(limit);
                List<Product> list = new ArrayList<>();

                for (BestSellerDTO o : orderItem) {
                    Product p = productRepository.findById(o.getProduct_id()).get();
                    p.setPrice(o.getValue());
                    p.setStock(o.getQuantity());

                    list.add(p);
                }

                return ResponseEntity.ok().body(list);
	}

}
