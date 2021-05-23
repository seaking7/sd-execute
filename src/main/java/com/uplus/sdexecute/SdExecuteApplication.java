package com.uplus.sdexecute;

import com.uplus.sdexecute.jpa.JdbcExecuteRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class SdExecuteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdExecuteApplication.class, args);
	}


}
