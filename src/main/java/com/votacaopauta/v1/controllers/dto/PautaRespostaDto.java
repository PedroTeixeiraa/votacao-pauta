package com.votacaopauta.v1.controllers.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PautaRespostaDto implements Serializable {

	private Long id;
}
