package com.votacaopauta.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.domain.Pauta;

public interface PautaRepository extends ReactiveCrudRepository<Pauta, Long> {
}
