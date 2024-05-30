package com.votacaopauta.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.domain.Pauta;
import com.votacaopauta.domain.SessaoVotacao;
import reactor.core.publisher.Mono;

public interface PautaRepository extends ReactiveCrudRepository<Pauta, Long> {

	Mono<Pauta> findById(UUID id);
}
