package com.votacaopauta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.votacaopauta.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.services.VotacaoService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("votacao")
public class VotacaoController {

	@Autowired
	private VotacaoService votacaoService;

	@PostMapping
	public void votar(@Valid @RequestBody VotoRequisicaoDto votoRequisicaoDto) {
		votacaoService.votar(votoRequisicaoDto);
	}
}
