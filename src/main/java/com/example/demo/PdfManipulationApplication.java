package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
@SpringBootApplication
public class PdfManipulationApplication {

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		SpringApplication.run(PdfManipulationApplication.class, args);
	}

}
