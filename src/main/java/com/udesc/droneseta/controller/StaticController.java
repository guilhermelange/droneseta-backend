package com.udesc.droneseta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class StaticController {
	@Autowired
	private ResourceLoader resourceLoader;

	@GetMapping(value = "/static/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody byte[] getImage(@PathVariable String name) throws IOException {
		String endpoint = (new File("")).getAbsolutePath() + "\\src\\main\\resources\\static\\" + name;
		File file = new File(endpoint);

		return Files.readAllBytes(file.toPath());
	}
}
