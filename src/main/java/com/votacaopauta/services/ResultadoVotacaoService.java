package com.votacaopauta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.ResultadoVotacaoRespostaDto;
import com.votacaopauta.domain.SessaoVotacao;
import com.votacaopauta.domain.Voto;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Service
public class ResultadoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@Autowired
	private VotoRepository votoRepository;

	public Mono<ResultadoVotacaoRespostaDto> buscar(Long pautaId) {
		return sessaoVotacaoService.possuiSessaoAtiva(pautaId)
				.flatMap(sessaoAtiva -> {
					if (Boolean.TRUE.equals(sessaoAtiva)) {
						return Mono.error(
								new BusinessException("A sessão de votação não pode ser iniciada, pois já existe uma sessão de votação ativa."));
					} else {
						return sessaoVotacaoRepository.findAllByPautaId(pautaId)
								.collectList()
								.flatMap(sessoes -> {
									List<Long> sessaoIds = sessoes.stream().map(SessaoVotacao::getId).toList();
									return votoRepository.findBySessaoVotacaoIdIn(sessaoIds)
											.collectList()
											.map(this::calcularResultado);
								});
					}
				});
	}

	private ResultadoVotacaoRespostaDto calcularResultado(List<Voto> votos) {
		int totalSim = 0;
		int totalNao = 0;

		for (Voto voto : votos) {
			if (voto.isOpcao()) {
				totalSim++;
			} else {
				totalNao++;
			}
		}

		int totalVotos = totalSim + totalNao;

		ResultadoVotacaoRespostaDto resultado = new ResultadoVotacaoRespostaDto();
		resultado.setTotalSim(totalSim);
		resultado.setTotalNao(totalNao);
		resultado.setTotalVotos(totalVotos);

		return resultado;
	}
}
