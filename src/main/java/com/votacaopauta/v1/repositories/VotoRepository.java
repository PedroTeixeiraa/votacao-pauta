package com.votacaopauta.v1.repositories;

import java.util.List;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.votacaopauta.v1.domain.Voto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VotoRepository extends ReactiveCrudRepository<Voto, Long> {

	Mono<Boolean> existsBySessaoVotacaoIdAndCpf(Long sessaoVotacaoId, String cpf);

	Mono<Long> countBySessaoVotacaoIdAndOpcao(Long sessaoVotacaoId, boolean opcao);

	Flux<Voto> findBySessaoVotacaoIdIn(List<Long> sessaoVotacaoIds);
}
