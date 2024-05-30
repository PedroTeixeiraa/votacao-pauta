package com.votacaopauta.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class SessaoVotacaoRequisicaoDto {

	@NotEmpty(message = "idPauta não pode ser nulo")
	@Size(max = 36, message = "O idPauta deve ter no máximo {max} caracteres")
	private String idPauta;

	private Integer tempoDuracao;
}
