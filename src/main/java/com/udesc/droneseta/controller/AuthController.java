package com.udesc.droneseta.controller;

import com.udesc.droneseta.config.JwtTokenUtil;
import com.udesc.droneseta.config.PasswordSecure;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.dto.AuthDTO;
import com.udesc.droneseta.model.dto.AuthResponseDTO;
import com.udesc.droneseta.model.error.ApplicationException;
import com.udesc.droneseta.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private CustomerRepository repository;

        @Autowired
        private JwtTokenUtil jwtTokenUtil;

	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody AuthDTO customerDTO) throws Exception {
		Optional<Customer> findCustomer = Optional.ofNullable(repository.findFirstByCpfEquals(customerDTO.getCpf().trim()));

		if (findCustomer.isEmpty()) {
			throw new ApplicationException("Usuário não encontrado", HttpStatus.BAD_REQUEST);
		}

		boolean validate = PasswordSecure.validate(customerDTO.getPassword(), findCustomer.get().getPassword());
		if (!validate) {
			throw new ApplicationException("Dados inválidos", HttpStatus.BAD_REQUEST);
		}

                final String token = jwtTokenUtil.generateToken(findCustomer.get());
		AuthResponseDTO response = new AuthResponseDTO(token, findCustomer.get());

		return ResponseEntity.ok().body(response);
        }

}