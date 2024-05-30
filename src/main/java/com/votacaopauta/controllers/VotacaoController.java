package com.votacaopauta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacaopauta.controllers.dto.ResultadoVotacaoRespostaDto;
import com.votacaopauta.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.controllers.dto.VotoRespostaDto;
import com.votacaopauta.services.ResultadoVotacaoService;
import com.votacaopauta.services.VotacaoService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("votacao")
public class VotacaoController {

	@Autowired
	private VotacaoService votacaoService;

	@Autowired
	private ResultadoVotacaoService resultadoVotacaoService;

	@PostMapping
	public Mono<VotoRespostaDto> votar(@Valid @RequestBody VotoRequisicaoDto votoRequisicaoDto) {
		return votacaoService.votar(votoRequisicaoDto);
	}

	@GetMapping("{pautaId}/resultado")
	public Mono<ResultadoVotacaoRespostaDto> buscar(@PathVariable Long pautaId) {
		return resultadoVotacaoService.buscar(pautaId);
	}


}
