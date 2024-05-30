package com.votacaopauta.v1.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacaopauta.v1.controllers.dto.PautaRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.PautaRespostaDto;
import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.v1.repositories.PautaRepository;
import com.votacaopauta.v1.services.SalvarPautaService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(PautaController.class)
@Import(SalvarPautaService.class)
class PautaControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	PautaRepository repository;

	@Test
	void deveSalvarPauta() {
		Pauta pauta = Pauta.builder()
				.id(1L)
				.build();

		when(repository.save(any())).thenReturn(Mono.just(pauta));

		PautaRequisicaoDto requisicaoDto = new PautaRequisicaoDto("Título da Pauta");

		webClient.post()
				.uri("/v1/pauta")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.id").isEqualTo(1);

		verify(repository, times(1)).save(any());
	}

	@Test
	void deveMostrarMensagemErroQuandoNaoPreencherTitulo() {
		Pauta pauta = Pauta.builder()
				.id(1L)
				.build();

		when(repository.save(any())).thenReturn(Mono.just(pauta));

		PautaRequisicaoDto requisicaoDto = new PautaRequisicaoDto();

		webClient.post()
				.uri("/v1/pauta")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody()
				.json("{\"mensagem\": \"Título não pode ser nulo\"}");;
	}
}
