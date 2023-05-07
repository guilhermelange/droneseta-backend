package com.udesc.droneseta.controller;

import com.udesc.droneseta.config.JwtTokenUtil;
import com.udesc.droneseta.model.Customer;
import com.udesc.droneseta.model.dto.AuthDTO;
import com.udesc.droneseta.model.dto.AuthResponseDTO;
import com.udesc.droneseta.model.error.ApplicationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/admin")
public class AuthAdminController {

        @Autowired
        private JwtTokenUtil jwtTokenUtil;

	@PostMapping("")
	public ResponseEntity<?> create(@Valid @RequestBody AuthDTO customerDTO) throws Exception {

                if (customerDTO.getCpf().equals("12345678910") && customerDTO.getPassword().equals("admin")) {
                    Customer admin = new Customer(0, "admin", customerDTO.getCpf(), "", "");
                    final String token = jwtTokenUtil.generateToken(admin);
                    AuthResponseDTO response = new AuthResponseDTO(token, admin);

                    return ResponseEntity.ok().body(response);
                }

                throw new ApplicationException("Dados inválidos", HttpStatus.BAD_REQUEST);
        }

}