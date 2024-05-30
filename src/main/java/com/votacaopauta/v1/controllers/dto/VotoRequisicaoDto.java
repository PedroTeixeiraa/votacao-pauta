package com.votacaopauta.v1.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class VotoRequisicaoDto {

	@NotBlank(message = "Voto não pode ser nulo")
	private String opcao;

	@NotEmpty(message = "Cpf não pode ser nulo")
	@Size(max = 11, message = "O CPF deve ter no máximo {max} caracteres")
	private String cpf;

	@NotNull(message = "idSessaoVotacao não pode ser nulo")
	private Long idSessaoVotacao;
}
