package com.votacaopauta.v1.controllers.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class PautaRequisicaoDto {

	@NotEmpty(message = "Título não pode ser nulo")
	private String titulo;
}
