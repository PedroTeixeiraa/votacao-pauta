package com.votacaopauta.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.domain.SessaoVotacao;
import reactor.core.publisher.Mono;

public interface SessaoVotacaoRepository extends ReactiveCrudRepository<SessaoVotacao, Long> {

	@Query("SELECT * FROM sessao_votacao WHERE pauta_id = :pautaId AND fim <= :dataAtual")
	Mono<SessaoVotacao> buscarSessaoVotacaoAtiva(@Param("pautaId") UUID pautaId, @Param("dataAtual") LocalDateTime dataAtual);
}