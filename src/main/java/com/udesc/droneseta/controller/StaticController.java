package com.udesc.droneseta.controller;

import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.Order;
import com.udesc.droneseta.model.dto.OrderDTO;
import com.udesc.droneseta.model.dto.OrderStatusDTO;
import com.udesc.droneseta.model.enumerator.OrderStatus;
import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.repository.CustomerRepository;
import com.udesc.droneseta.repository.OrderRepository;
import com.udesc.droneseta.service.OrderReportService;
import jakarta.validation.Valid;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class StaticController {
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping(value = "/images/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable String name) throws IOException {
		InputStream in = getClass()
				.getResourceAsStream((new File("teste")).getAbsolutePath() + "\\product_2.jpg");

		return IOUtils.toByteArray(in);


	}
}
