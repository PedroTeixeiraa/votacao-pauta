package com.votacaopauta.v1.controllers.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ResultadoVotacaoRespostaDto {

	private Integer totalSim;
	private Integer totalNao;
	private Integer totalVotos;
}
