package com.votacaopauta.v1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.votacaopauta.v1.controllers.dto.ResultadoVotacaoRespostaDto;
import com.votacaopauta.v1.domain.SessaoVotacao;
import com.votacaopauta.v1.domain.Voto;
import com.votacaopauta.v1.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ResultadoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private BuscarVotoService buscarVotoService;

	@Autowired
	private ValidarSessaoVotacaoService validarSessaoVotacaoService;

	public Mono<ResultadoVotacaoRespostaDto> gerar(Long pautaId) {
		return validarSessaoVotacaoService.validarSessaoAtiva(pautaId)
				.flatMap(sessaoAtiva -> sessaoVotacaoRepository.findAllByPautaId(pautaId)
						.collectList()
						.flatMap(sessoes -> {
							List<Long> sessaoIds = sessoes.stream().map(SessaoVotacao::getId).toList();
							return buscarVotoService.buscarVotos(sessaoIds)
									.collectList()
									.map(this::calcularResultado);
						}));
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

		log.info("Resultado da pauta gerado com sucesso: {}", resultado);

		return resultado;
	}
}
