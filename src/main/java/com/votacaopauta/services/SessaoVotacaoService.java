package com.votacaopauta.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.SessaoVotacaoRespostaDto;
import com.votacaopauta.domain.SessaoVotacao;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.PautaRepository;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Mono;

@Service
public class SessaoVotacaoService {

	private static final Integer TEMPO_DURACAO_PADRAO = 1;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<Void> iniciar(String idPauta, Integer tempoDuracao) {
		UUID pautaId = UUID.fromString(idPauta);

		pautaRepository.findById(pautaId).switchIfEmpty(Mono.error(new BusinessException("Pauta não encontrada"))).flatMap(pauta -> {
			Integer duracao = calcularTempoDuracaoSessao(tempoDuracao);

			SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
					.pautaId(pauta.getId())
					.inicio(LocalDateTime.now())
					.fim(LocalDateTime.now().plusMinutes(duracao))
					.duracao(duracao)
					.build();

			buscarSessaoVotacaoAtiva(pautaId)
					.switchIfEmpty(sessaoVotacaoRepository.save(sessaoVotacao)) // Se não retornar nada, emite null
					.flatMap(sessaoVotacaoAtiva -> {
						// Se existir sessão ativa, retorna Mono.empty()
						if (sessaoVotacaoAtiva != null) {
							Mono.error(new BusinessException("Não é possível salvar a sessão de votação porque não há sessão de votação ativa."));
						}

						return Mono.just(sessaoVotacaoAtiva);
					});
			return Mono.empty();
		});

		return Mono.empty();
	}


	private Integer calcularTempoDuracaoSessao(Integer tempoDuracao) {
		return Optional.ofNullable(tempoDuracao).orElse(TEMPO_DURACAO_PADRAO);
	}

	public Mono<SessaoVotacao> buscarSessaoVotacaoAtiva(UUID pautaId) {
		LocalDateTime dataAtual = LocalDateTime.now();
		return sessaoVotacaoRepository.buscarSessaoVotacaoAtiva(pautaId, dataAtual);
	}
}
