package com.votacaopauta.v1.domain;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Table("pauta")
public class Pauta {

	@Id
	private Long id;
	private String titulo;
	private LocalDateTime dataCriacao;
}
