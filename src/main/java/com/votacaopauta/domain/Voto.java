package com.votacaopauta.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table("voto")
public class Voto {

	@Id
	private Long id;
	private Long sessaoVotacaoId;
	private String cpf;
	private boolean opcao;
	private LocalDateTime dataVoto;
}
