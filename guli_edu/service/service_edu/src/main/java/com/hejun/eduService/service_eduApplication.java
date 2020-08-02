package com.hejun.eduService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = {"com.hejun"})
public class service_eduApplication {

	public static void main(String[] args) {
		SpringApplication.run(service_eduApplication.class, args);
	}

}
