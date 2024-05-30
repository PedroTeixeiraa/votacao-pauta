package com.votacaopauta.v1.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.controllers.dto.PautaRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.SessaoVotacaoRequisicaoDto;
import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.v1.repositories.PautaRepository;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import com.votacaopauta.v1.services.BuscarPautaService;
import com.votacaopauta.v1.services.SalvarPautaService;
import com.votacaopauta.v1.services.SessaoVotacaoService;
import com.votacaopauta.v1.services.ValidarSessaoVotacaoService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SessaoVotacaoController.class)
@Import(SessaoVotacaoService.class)
class SessaoVotacaoControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	BuscarPautaService buscarPautaService;

	@MockBean
	ValidarSessaoVotacaoService validarSessaoVotacaoService;

	@MockBean
	SessaoVotacaoRepository sessaoVotacaoRepository;

	@Test
	void deveIniciarSessaoVotacao() {
		Long pautaId = 2L;
		Integer tempoDuracao = 1;

		Pauta pauta = Pauta.builder().id(pautaId).build();

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder().id(1L).pautaId(pautaId).build();

		when(buscarPautaService.buscar(pautaId)).thenReturn(Mono.just(pauta));
		when(validarSessaoVotacaoService.validarSessaoAtiva(pautaId)).thenReturn(Mono.just(true));
		when(sessaoVotacaoRepository.save(any())).thenReturn(Mono.just(sessaoVotacao));

		SessaoVotacaoRequisicaoDto requisicaoDto = new SessaoVotacaoRequisicaoDto(pautaId, tempoDuracao);

		webClient.post()
				.uri("/v1/sessao-votacao/iniciar")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.id")
				.isEqualTo(1);

		verify(sessaoVotacaoRepository, times(1)).save(any());
	}

	@Test
	void deveRetornarErroQuandoNaoEncontrarPauta() {
		Long pautaId = 2L;
		Integer tempoDuracao = 1;

		when(buscarPautaService.buscar(pautaId)).thenReturn(Mono.error(new BusinessException("Pauta não encontrada!")));

		SessaoVotacaoRequisicaoDto requisicaoDto = new SessaoVotacaoRequisicaoDto(pautaId, tempoDuracao);

		webClient.post()
				.uri("/v1/sessao-votacao/iniciar")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("Pauta não encontrada!");
	}

	@Test
	void deveRetornarErroQuandoEncontrarUmaSessaoVotacaoAtivaParaMesmaPauta() {
		Long pautaId = 2L;
		Integer tempoDuracao = 1;

		Pauta pauta = Pauta.builder().id(pautaId).build();

		when(buscarPautaService.buscar(pautaId)).thenReturn(Mono.just(pauta));
		when(validarSessaoVotacaoService.validarSessaoAtiva(pautaId)).thenReturn(Mono.error(new BusinessException("Não é possível continuar devido à existência de uma sessão de votação ativa.")));

		SessaoVotacaoRequisicaoDto requisicaoDto = new SessaoVotacaoRequisicaoDto(pautaId, tempoDuracao);

		webClient.post()
				.uri("/v1/sessao-votacao/iniciar")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("Não é possível continuar devido à existência de uma sessão de votação ativa.");
	}
}
