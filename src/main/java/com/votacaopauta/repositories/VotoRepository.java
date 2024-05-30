package com.votacaopauta.repositories;

import java.util.UUID;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.domain.Voto;
import reactor.core.publisher.Mono;

public interface VotoRepository extends ReactiveCrudRepository<Voto, Long> {

	@Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM voto WHERE sessao_votacao_id = :sessaoVotacaoId AND cpf_usuario = :cpfUsuario")
	Mono<Boolean> usuarioHabilitadoParaVotar(UUID sessaoVotacaoId, String cpfUsuario);
}
