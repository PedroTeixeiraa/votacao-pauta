package com.votacaopauta.controllers.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.votacaopauta.desserializers.VotoDeserializer;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class VotoRequisicaoDto {

	@NotNull(message = "Voto não pode ser nulo")
	@JsonDeserialize(using = VotoDeserializer.class)
	private Boolean opcao;

	@NotEmpty(message = "Cpf não pode ser nulo")
	private String cpf;

	@NotNull(message = "idPauta não pode ser nulo")
	private UUID idPauta;
}
