package com.votacaopauta.v1.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.votacaopauta.comum.desserializers.VotoDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class VotoRequisicaoDto {

	@NotNull(message = "Voto não pode ser nulo")
	@JsonDeserialize(using = VotoDeserializer.class)
	private Boolean opcao;

	@NotEmpty(message = "Cpf não pode ser nulo")
	@Size(max = 11, message = "O CPF deve ter no máximo {max} caracteres")
	private String cpf;

	@NotNull(message = "idSessaoVotacao não pode ser nulo")
	private Long idSessaoVotacao;
}
