package com.votacaopauta.v1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.comum.exceptions.BusinessException;
import reactor.core.publisher.Mono;

@Service
public class VerificarUsuarioHabilitadoVotacaoService {

	@Autowired
	private BuscarSessaoVotacaoService buscarSessaoVotacaoService;

	@Autowired
	private VerificarVotoService verificarVotoService;

	public Mono<Boolean> verificar(String cpf) {
		LocalDateTime dataAtual = LocalDateTime.now();

		return buscarSessaoVotacaoService.buscarTodos()
				.filter(sessao -> sessao.getFim().isAfter(dataAtual))
				.flatMap(sessao -> verificarVotoService.verificarCpfVotouSessao(sessao.getId(), cpf))
				.flatMap(votou -> {
					if (Boolean.TRUE.equals(votou)) {
						return Mono.error(new BusinessException(
								"Desculpe, você já participou de uma votação ativa. Por favor, aguarde o término dessa sessão para votar novamente."));
					} else {
						return Mono.just(true);
					}
				})
				.any(votou -> votou);
	}
}
