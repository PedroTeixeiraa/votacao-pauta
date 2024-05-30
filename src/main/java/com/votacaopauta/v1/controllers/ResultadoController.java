package com.votacaopauta.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.extern.slf4j.Slf4j;

import com.votacaopauta.v1.controllers.dto.ResultadoVotacaoRespostaDto;
import com.votacaopauta.v1.services.ResultadoVotacaoService;
import reactor.core.publisher.Mono;

@Slf4j
@Validated
@RestController
@RequestMapping("v1/resultado")
@Tag(name = "Resultado Pauta", description = "Endpoints relacionados a Resultado da pauta")
public class ResultadoController {

	@Autowired
	private ResultadoVotacaoService resultadoVotacaoService;

	@GetMapping("{pautaId}")
	@Operation(summary = "Resultado pauta", description = "Retorna o resultado dos votos relacionados a uma pauta.")
	public Mono<ResultadoVotacaoRespostaDto> gerarResultado(@PathVariable Long pautaId) {
		log.info("Requisição recebida para gerar resultado pauta: {}", pautaId);
		return resultadoVotacaoService.gerar(pautaId);
	}


}
