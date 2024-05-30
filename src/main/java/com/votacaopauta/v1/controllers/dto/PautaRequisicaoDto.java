package com.votacaopauta.v1.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PautaRequisicaoDto {

	@NotEmpty(message = "Título não pode ser nulo")
	private String titulo;
}
