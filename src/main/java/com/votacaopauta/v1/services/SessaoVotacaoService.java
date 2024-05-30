package com.votacaopauta.v1.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.v1.controllers.dto.SessaoVotacaoRespostaDto;
import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Mono;

@Service
public class SessaoVotacaoService {

	private static final Integer TEMPO_DURACAO_PADRAO = 1;

	@Autowired
	private BuscarPautaService buscarPautaService;

	@Autowired
	private ValidarSessaoVotacaoService validarSessaoVotacaoService;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<SessaoVotacaoRespostaDto> iniciar(Long idPauta, Integer tempoDuracao) {
		return buscarPautaService.buscar(idPauta).flatMap(pauta -> {
			Integer duracao = calcularTempoDuracaoSessao(tempoDuracao);

			SessaoVotacao sessaoVotacao = criarSessaoVotacao(idPauta, duracao);

			return validarSessaoVotacaoService.validarSessaoAtiva(idPauta)
					.then(sessaoVotacaoRepository.save(sessaoVotacao))
					.map(SessaoVotacao::getId)
					.map(id -> SessaoVotacaoRespostaDto.builder().id(sessaoVotacao.getId()).build());
		});
	}

	private Integer calcularTempoDuracaoSessao(Integer tempoDuracao) {
		return Optional.ofNullable(tempoDuracao).orElse(TEMPO_DURACAO_PADRAO);
	}

	private SessaoVotacao criarSessaoVotacao(Long idPauta, Integer duracao) {
		return SessaoVotacao.builder()
				.pautaId(idPauta)
				.inicio(LocalDateTime.now())
				.fim(LocalDateTime.now().plusMinutes(duracao))
				.duracao(duracao)
				.build();
	}

}
