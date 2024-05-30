package com.votacaopauta.v1.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class SessaoVotacaoRequisicaoDto {

	@NotNull(message = "idPauta não pode ser nulo")
	private Long idPauta;

	private Integer tempoDuracao;
}
