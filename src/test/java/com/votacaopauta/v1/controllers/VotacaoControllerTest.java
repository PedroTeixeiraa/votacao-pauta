package com.votacaopauta.v1.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.controllers.dto.SessaoVotacaoRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.v1.domain.Voto;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import com.votacaopauta.v1.repositories.VotoRepository;
import com.votacaopauta.v1.services.BuscarPautaService;
import com.votacaopauta.v1.services.BuscarSessaoVotacaoService;
import com.votacaopauta.v1.services.ResultadoVotacaoService;
import com.votacaopauta.v1.services.SessaoVotacaoService;
import com.votacaopauta.v1.services.ValidarSessaoVotacaoService;
import com.votacaopauta.v1.services.VerificarUsuarioHabilitadoVotacaoService;
import com.votacaopauta.v1.services.VotacaoService;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(VotacaoController.class)
@Import({ VotacaoService.class })
class VotacaoControllerTest {

	@Autowired
	private WebTestClient webClient;

	@MockBean
	VotoRepository votoRepository;

	@MockBean
	BuscarSessaoVotacaoService buscarSessaoVotacaoService;

	@MockBean
	VerificarUsuarioHabilitadoVotacaoService verificarUsuarioHabilitadoVotacaoService;

	@Test
	void deveRealizarVotacao() {
		Long idSessaoVotacao = 1L;
		String cpf = "123213";
		VotoRequisicaoDto requisicaoDto = new VotoRequisicaoDto(true, cpf, idSessaoVotacao);

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder().fim(LocalDateTime.now().plusMinutes(1L)).build();

		Voto voto = Voto.builder().build();

		when(buscarSessaoVotacaoService.buscar(idSessaoVotacao)).thenReturn(Mono.just(sessaoVotacao));
		when(verificarUsuarioHabilitadoVotacaoService.verificar(cpf)).thenReturn(Mono.just(true));
		when(votoRepository.save(any())).thenReturn(Mono.just(voto));

		webClient.post()
				.uri("/v1/votacao")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("Seu voto foi registrado com sucesso.");

		verify(votoRepository, times(1)).save(any());
	}

	@Test
	void deveRetornarMensagemErroQuandoNaoEncontrarSessaoVotacao() {
		Long idSessaoVotacao = 1L;
		String cpf = "123213";
		VotoRequisicaoDto requisicaoDto = new VotoRequisicaoDto(true, cpf, idSessaoVotacao);

		when(buscarSessaoVotacaoService.buscar(idSessaoVotacao)).thenReturn(
				Mono.error(new BusinessException("A sessão de votação não foi encontrada.")));

		webClient.post()
				.uri("/v1/votacao")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("A sessão de votação não foi encontrada.");
	}

	@Test
	void deveRetornarMensagemErroQuandoSessaoVotacaoNaoEstiverAtiva() {
		Long idSessaoVotacao = 1L;
		String cpf = "123213";
		VotoRequisicaoDto requisicaoDto = new VotoRequisicaoDto(true, cpf, idSessaoVotacao);

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder().fim(LocalDateTime.now().minusMinutes(1L)).build();

		when(buscarSessaoVotacaoService.buscar(idSessaoVotacao)).thenReturn(Mono.just(sessaoVotacao));

		webClient.post()
				.uri("/v1/votacao")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("A sessão de votação não está mais ativa.");
	}

	@Test
	void deveRetornarMensagemErroQuandoUsuarioNaoEstiverHabilitadoParaVotar() {
		Long idSessaoVotacao = 1L;
		String cpf = "123213";
		VotoRequisicaoDto requisicaoDto = new VotoRequisicaoDto(true, cpf, idSessaoVotacao);

		SessaoVotacao sessaoVotacao = SessaoVotacao.builder().fim(LocalDateTime.now().plusMinutes(1L)).build();

		when(buscarSessaoVotacaoService.buscar(idSessaoVotacao)).thenReturn(Mono.just(sessaoVotacao));
		when(verificarUsuarioHabilitadoVotacaoService.verificar(cpf)).thenReturn(Mono.error(new BusinessException(
				"Desculpe, você já participou de uma votação ativa. Por favor, aguarde o término dessa sessão para votar novamente.")));

		webClient.post()
				.uri("/v1/votacao")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(requisicaoDto)
				.exchange()
				.expectStatus()
				.isBadRequest()
				.expectBody()
				.jsonPath("$.mensagem")
				.isEqualTo("Desculpe, você já participou de uma votação ativa. Por favor, aguarde o término dessa sessão para votar novamente.");
	}
}
