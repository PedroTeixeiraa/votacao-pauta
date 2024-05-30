package com.votacaopauta.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.PautaRespostaDto;
import com.votacaopauta.domain.Pauta;
import com.votacaopauta.repositories.PautaRepository;
import reactor.core.publisher.Mono;

@Service
public class PautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Mono<PautaRespostaDto> salvar(String titulo) {
		Pauta pauta = Pauta.builder().titulo(titulo).dataCriacao(LocalDateTime.now()).build();

		return pautaRepository.save(pauta).map(Pauta::getId).map(id -> PautaRespostaDto.builder().id(id).build());
	}

}
