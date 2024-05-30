package com.votacaopauta.v1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.v1.controllers.dto.PautaRespostaDto;
import com.votacaopauta.v1.domain.Pauta;
import com.votacaopauta.v1.repositories.PautaRepository;
import reactor.core.publisher.Mono;

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
		return PautaRespostaDto.builder().id(pauta.getId()).build();
	}

}
