package com.votacaopauta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.domain.Pauta;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.PautaRepository;
import reactor.core.publisher.Mono;

@Service
public class BuscarPautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Mono<Pauta> buscar(Long idPauta) {
		return pautaRepository.findById(idPauta)
				.switchIfEmpty(Mono.error(new BusinessException("Pauta não encontrada!")))
				.flatMap(Mono::just);
	}
}
