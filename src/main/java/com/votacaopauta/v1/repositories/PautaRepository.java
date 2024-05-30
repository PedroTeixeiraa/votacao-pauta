package com.votacaopauta.v1.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.v1.domain.Pauta;

public interface PautaRepository extends ReactiveCrudRepository<Pauta, Long> {
}
