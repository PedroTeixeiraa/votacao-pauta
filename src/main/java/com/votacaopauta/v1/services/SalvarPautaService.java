package com.votacaopauta.v1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.votacaopauta.v1.controllers.dto.PautaRespostaDto;
import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.v1.repositories.PautaRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SalvarPautaService {

	@Autowired
	private PautaRepository pautaRepository;

	public Mono<PautaRespostaDto> salvar(String titulo) {
		Pauta pauta = criarPauta(titulo);

		return salvarPauta(pauta);
	}

	private Pauta criarPauta(String titulo) {
		return Pauta.builder().titulo(titulo).dataCriacao(LocalDateTime.now()).build();
	}

	private Mono<PautaRespostaDto> salvarPauta(Pauta pauta) {
		return pautaRepository.save(pauta).map(this::mapearParaRespostaDto);
	}

	private PautaRespostaDto mapearParaRespostaDto(Pauta pauta) {
		log.info("Pauta criada: {}", pauta);
		return new PautaRespostaDto(pauta.getId());
	}

}
