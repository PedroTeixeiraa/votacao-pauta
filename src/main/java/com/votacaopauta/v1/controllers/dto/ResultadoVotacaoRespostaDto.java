package com.votacaopauta.v1.controllers.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ResultadoVotacaoRespostaDto {

	private Integer totalSim;
	private Integer totalNao;
	private Integer totalVotos;
}
