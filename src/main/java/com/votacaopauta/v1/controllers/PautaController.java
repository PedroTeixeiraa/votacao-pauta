package com.votacaopauta.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.votacaopauta.v1.controllers.dto.PautaRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.PautaRespostaDto;
import com.votacaopauta.v1.services.SalvarPautaService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("v1/pauta")
public class PautaController {

	@Autowired
	private SalvarPautaService salvarPautaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<PautaRespostaDto> salvar(@Valid @RequestBody PautaRequisicaoDto pautaRequisicaoDto) {
		return salvarPautaService.salvar(pautaRequisicaoDto.getTitulo());
	}
}
