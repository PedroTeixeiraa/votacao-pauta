package com.votacaopauta.v1.repositories;

import java.time.LocalDateTime;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.v1.domain.SessaoVotacao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SessaoVotacaoRepository extends ReactiveCrudRepository<SessaoVotacao, Long> {

	@Query("SELECT * FROM sessao_votacao WHERE pauta_id = :pautaId AND fim <= :dataAtual")
	Mono<SessaoVotacao> buscarSessaoVotacaoAtiva(@Param("pautaId") Long pautaId, @Param("dataAtual") LocalDateTime dataAtual);

	Flux<SessaoVotacao> findAllByPautaId(Long pautaId);
}
