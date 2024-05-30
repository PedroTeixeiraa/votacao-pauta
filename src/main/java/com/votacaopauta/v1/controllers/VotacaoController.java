package com.votacaopauta.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.votacaopauta.v1.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.VotoRespostaDto;
import com.votacaopauta.v1.services.VotacaoService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("v1/votacao")
@Tag(name = "Votação", description = "Endpoints relacionados a Votação")
public class VotacaoController {

	@Autowired
	private VotacaoService votacaoService;

	@PostMapping
	@Operation(summary = "Votar pauta", description = "Retorna uma mensagem de sucesso quando voto realizado.")
	public Mono<VotoRespostaDto> votar(@Valid @RequestBody VotoRequisicaoDto votoRequisicaoDto) {
		return votacaoService.votar(votoRequisicaoDto);
	}

}
