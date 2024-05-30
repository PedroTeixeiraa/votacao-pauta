package com.votacaopauta.v1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ValidarSessaoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<Boolean> validarSessaoAtiva(Long idPauta) {
		return possuiSessaoAtiva(idPauta)
				.flatMap(sessaoAtiva -> {
					if (Boolean.TRUE.equals(sessaoAtiva)) {
						return Mono.error(
								new BusinessException("Não é possível continuar devido à existência de uma sessão de votação ativa."));
					} else {
						return Mono.just(true);
					}
				});
	}

	private Mono<Boolean> possuiSessaoAtiva(Long pautaId) {
		Flux<SessaoVotacao> sessoes = sessaoVotacaoRepository.findAllByPautaId(pautaId);
		LocalDateTime dataAtual = LocalDateTime.now();

		return sessoes.filter(sessao -> sessao.getFim().isAfter(dataAtual)).hasElements().map(hasElements -> hasElements);
	}
}
