package com.mirzaakhena.batchsystem;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mirzaakhena.batchsystem.service.InitService;

@SpringBootApplication
public class MainApplicationManufacture {

	public static void main(String[] args) {
		SpringApplication.run(MainApplicationManufacture.class, args);
	}
	
	@Autowired
	InitService initService;
	
	@PostConstruct
	private void init() throws Exception {
		initService.doInit();
	}

}
