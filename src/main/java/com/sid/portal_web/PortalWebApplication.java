package com.sid.portal_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // aqui habilitamos el cache para mayor rendimiento
public class PortalWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalWebApplication.class, args);
	}

}
