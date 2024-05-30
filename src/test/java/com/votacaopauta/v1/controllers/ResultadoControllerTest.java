package com.votacaopauta.v1.controllers;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.v1.domain.Voto;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import com.votacaopauta.v1.services.BuscarVotoService;
import com.votacaopauta.v1.services.ResultadoVotacaoService;
import com.votacaopauta.v1.services.ValidarSessaoVotacaoService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ResultadoController.class)
@Import({ ResultadoVotacaoService.class })
class ResultadoControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	SessaoVotacaoRepository sessaoVotacaoRepository;

	@MockBean
	BuscarVotoService buscarVotoService;

	@MockBean
	ValidarSessaoVotacaoService validarSessaoVotacaoService;

	@Test
	void deveGerarResultadoVotacao() {
		Long pautaId = 1L;
		Long sessaoVotacaoId = 2L;

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
				.id(sessaoVotacaoId)
				.pautaId(pautaId)
				.build();

		Voto voto1 = Voto.builder().opcao(true).build();
		Voto voto2 = Voto.builder().opcao(true).build();
		Voto voto3 = Voto.builder().opcao(true).build();
		Voto voto4 = Voto.builder().opcao(false).build();

		when(validarSessaoVotacaoService.validarSessaoAtiva(pautaId)).thenReturn(Mono.just(true));
		when(sessaoVotacaoRepository.findAllByPautaId(pautaId)).thenReturn(Flux.just(sessaoVotacao));
		when(buscarVotoService.buscarVotos(Collections.singletonList(sessaoVotacaoId))).thenReturn(Flux.just(voto1, voto2, voto3, voto4));

		webClient.get()
				.uri("/v1/resultado/1")
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.totalSim").isEqualTo(3)
				.jsonPath("$.totalNao").isEqualTo(1)
				.jsonPath("$.totalVotos").isEqualTo(4);
	}

	@Test
	void deveRetornarMensagemErroQuandoNaoEncontrarSessaoVotacaoAtiva() {
		Long pautaId = 1L;
		when(validarSessaoVotacaoService.validarSessaoAtiva(pautaId)).thenReturn(
				Mono.error(new BusinessException("Não é possível continuar devido à existência de uma sessão de votação ativa.")));

		webClient.get()
				.uri("/v1/resultado/1")
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("Não é possível continuar devido à existência de uma sessão de votação ativa.");
	}

}
