package com.votacaopauta.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.SessaoVotacaoRespostaDto;
import com.votacaopauta.domain.SessaoVotacao;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.PautaRepository;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SessaoVotacaoService {

	private static final Integer TEMPO_DURACAO_PADRAO = 1;

	@Autowired
	private PautaRepository pautaRepository;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<SessaoVotacaoRespostaDto> iniciar(Long idPauta, Integer tempoDuracao) {
		return pautaRepository.findById(idPauta).switchIfEmpty(Mono.error(new BusinessException("Pauta não encontrada!"))).flatMap(pauta -> {
			Integer duracao = calcularTempoDuracaoSessao(tempoDuracao);

			SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
					.pautaId(pauta.getId())
					.inicio(LocalDateTime.now())
					.fim(LocalDateTime.now().plusMinutes(duracao))
					.duracao(duracao)
					.build();

			return possuiSessaoAtiva(idPauta).flatMap(sessaoAtiva -> {
				if (Boolean.TRUE.equals(sessaoAtiva)) {
					return Mono.error(
							new BusinessException("A sessão de votação não pode ser iniciada, pois já existe uma sessão de votação ativa."));
				} else {
					return sessaoVotacaoRepository.save(sessaoVotacao)
							.map(SessaoVotacao::getId)
							.map(id -> SessaoVotacaoRespostaDto.builder().id(sessaoVotacao.getId()).build());
				}
			});
		});
	}


	private Integer calcularTempoDuracaoSessao(Integer tempoDuracao) {
		return Optional.ofNullable(tempoDuracao).orElse(TEMPO_DURACAO_PADRAO);
	}

	public Mono<Boolean> possuiSessaoAtiva(Long pautaId) {
		Flux<SessaoVotacao> sessoes = sessaoVotacaoRepository.findAllByPautaId(pautaId);
		LocalDateTime dataAtual = LocalDateTime.now();

		return sessoes.filter(sessao -> sessao.getFim().isAfter(dataAtual)).hasElements().map(hasElements -> hasElements);
	}
}
