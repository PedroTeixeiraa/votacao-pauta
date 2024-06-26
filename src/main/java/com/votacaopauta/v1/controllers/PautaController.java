package com.votacaopauta.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import com.votacaopauta.v1.controllers.dto.PautaRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.PautaRespostaDto;
import com.votacaopauta.v1.services.SalvarPautaService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@RestController
@RequestMapping("v1/pauta")
@Tag(name = "Pauta", description = "Endpoints relacionados a Pauta")
public class PautaController {

	@Autowired
	private SalvarPautaService salvarPautaService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Salvar pauta", description = "Retorna o id da pauta criada.")
	public Mono<PautaRespostaDto> salvar(@Valid @RequestBody PautaRequisicaoDto pautaRequisicaoDto) {
		log.info("Requisição recebida para criar pauta: {}", pautaRequisicaoDto);
		return salvarPautaService.salvar(pautaRequisicaoDto.getTitulo());
	}
}
