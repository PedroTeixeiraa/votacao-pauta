package com.votacaopauta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.ResultadoVotacaoRespostaDto;
import com.votacaopauta.domain.SessaoVotacao;
import com.votacaopauta.domain.Voto;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import reactor.core.publisher.Mono;

@Service
public class ResultadoVotacaoService {

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@Autowired
	private BuscarVotoService buscarVotoService;

	@Autowired
	private ValidarSessaoVotacaoService validarSessaoVotacaoService;

	public Mono<ResultadoVotacaoRespostaDto> buscar(Long pautaId) {
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

		return resultado;
	}
}
