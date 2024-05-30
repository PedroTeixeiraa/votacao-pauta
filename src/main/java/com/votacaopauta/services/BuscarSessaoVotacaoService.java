package com.votacaopauta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.domain.SessaoVotacao;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BuscarSessaoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<SessaoVotacao> buscar(Long idSessaoVotacao) {
		return sessaoVotacaoRepository.findById(idSessaoVotacao)
				.switchIfEmpty(Mono.error(new BusinessException("A sessão de votação não foi encontrada.")))
				.flatMap(Mono::just);
	}

	public Flux<SessaoVotacao> buscarTodos() {
		return sessaoVotacaoRepository.findAll();
	}
}
