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
@Table("sessao_votacao")
public class SessaoVotacao {

	@Id
	private Long id;
	private Long pautaId;
	private LocalDateTime inicio;
	private LocalDateTime fim;
	private Integer duracao;
}
