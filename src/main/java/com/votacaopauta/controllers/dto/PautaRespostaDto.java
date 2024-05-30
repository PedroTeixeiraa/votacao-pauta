package com.votacaopauta.controllers.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PautaRespostaDto implements Serializable {

	private UUID id;
}
