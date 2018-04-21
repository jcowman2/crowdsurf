package com.joecowman.crowdsurf

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CrowdsurfApplication {

	static void main(String[] args) {
		SpringApplication.run CrowdsurfApplication, args
	}
}
