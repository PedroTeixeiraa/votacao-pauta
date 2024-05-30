package com.votacaopauta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacaopauta.controllers.dto.SessaoVotacaoRequisicaoDto;
import com.votacaopauta.controllers.dto.SessaoVotacaoRespostaDto;
import com.votacaopauta.services.SessaoVotacaoService;
import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("sessao-votacao")
public class SessaoVotacaoController {

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@PostMapping("iniciar")
	public Mono<SessaoVotacaoRespostaDto> iniciarSessaoVotacao(@Valid @RequestBody SessaoVotacaoRequisicaoDto sessaoVotacaoRequisicaoDto) {
		Long idPauta = sessaoVotacaoRequisicaoDto.getIdPauta();
		Integer tempoSessao = sessaoVotacaoRequisicaoDto.getTempoDuracao();

		return sessaoVotacaoService.iniciar(idPauta, tempoSessao);
	}
}
