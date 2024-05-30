package com.votacaopauta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.domain.Voto;
import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Flux;

@Service
public class BuscarVotoService {

	@Autowired
	private VotoRepository votoRepository;

	public Flux<Voto> buscarVotos(List<Long> sessaoVotacaoIds) {
		return votoRepository.findBySessaoVotacaoIdIn(sessaoVotacaoIds);
	}
}
