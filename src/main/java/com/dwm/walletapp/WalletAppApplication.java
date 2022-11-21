package com.dwm.walletapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Wallet API", description = "Wallet balance service"))
public class WalletAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletAppApplication.class, args);
	}

}
