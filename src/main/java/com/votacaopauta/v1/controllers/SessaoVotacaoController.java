package com.votacaopauta.v1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.votacaopauta.v1.controllers.dto.SessaoVotacaoRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.SessaoVotacaoRespostaDto;
import com.votacaopauta.v1.services.SessaoVotacaoService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("v1/sessao-votacao")
@Tag(name = "Sessão Votação", description = "Endpoints relacionados a Sessão Votação")
public class SessaoVotacaoController {

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@PostMapping("iniciar")
	@Operation(summary = "Iniciar votação sessão", description = "Retorna o id da sessão votação criada.")
	public Mono<SessaoVotacaoRespostaDto> iniciarSessaoVotacao(@Valid @RequestBody SessaoVotacaoRequisicaoDto sessaoVotacaoRequisicaoDto) {
		Long idPauta = sessaoVotacaoRequisicaoDto.getIdPauta();
		Integer tempoSessao = sessaoVotacaoRequisicaoDto.getTempoDuracao();

		return sessaoVotacaoService.iniciar(idPauta, tempoSessao);
	}
}
