package com.votacaopauta.v1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.repositories.PautaRepository;
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
