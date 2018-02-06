package com.cmsz.sldk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cmsz.sldk.mapper")
public class CmszSldkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmszSldkApplication.class, args);
	}
}
