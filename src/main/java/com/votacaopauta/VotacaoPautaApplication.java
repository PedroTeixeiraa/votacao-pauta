package com.votacaopauta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class VotacaoPautaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotacaoPautaApplication.class, args);
	}

}
