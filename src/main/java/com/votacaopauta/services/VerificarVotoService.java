package com.votacaopauta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Service
public class VerificarVotoService {

	@Autowired
	private VotoRepository votoRepository;

	public Mono<Boolean> verificarCpfVotouSessao(Long idSessaoVotacao, String cpf) {
		return votoRepository.existsBySessaoVotacaoIdAndCpf(idSessaoVotacao, cpf);
	}
}
