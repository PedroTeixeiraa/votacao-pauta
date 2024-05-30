package com.votacaopauta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.votacaopauta.controllers.dto.PautaRequisicaoDto;
import com.votacaopauta.controllers.dto.PautaRespostaDto;
import com.votacaopauta.services.PautaService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("pauta")
public class PautaController {

	@Autowired
	private PautaService pautaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<PautaRespostaDto> salvar(@Valid @RequestBody PautaRequisicaoDto pautaRequisicaoDto) {
		return pautaService.salvar(pautaRequisicaoDto.getTitulo());
	}
}
