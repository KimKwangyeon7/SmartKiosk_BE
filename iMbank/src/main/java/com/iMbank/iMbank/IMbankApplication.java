package com.iMbank.iMbank;

import com.iMbank.iMbank.global.component.jwt.JwtTokenPropsInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(JwtTokenPropsInfo.class)
@SpringBootApplication
public class IMbankApplication {

	public static void main(String[] args) {
		SpringApplication.run(IMbankApplication.class, args);
	}

}
