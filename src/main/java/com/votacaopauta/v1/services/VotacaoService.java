package com.votacaopauta.v1.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import com.votacaopauta.comum.util.SimNaoUtil;
import com.votacaopauta.v1.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.v1.controllers.dto.VotoRespostaDto;
import com.votacaopauta.v1.domain.Voto;
import com.votacaopauta.comum.exceptions.BusinessException;
import com.votacaopauta.v1.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class VotacaoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private BuscarSessaoVotacaoService buscarSessaoVotacaoService;

	@Autowired
	private VerificarUsuarioHabilitadoVotacaoService verificarUsuarioHabilitadoVotacaoService;

	public Mono<VotoRespostaDto> votar(VotoRequisicaoDto votoRequisicaoDto) {
		Long idSessaoVotacao = votoRequisicaoDto.getIdSessaoVotacao();
		String cpf = votoRequisicaoDto.getCpf();
		boolean opcao = SimNaoUtil.converterBooleano(votoRequisicaoDto.getOpcao());

		return buscarSessaoVotacaoService.buscar(idSessaoVotacao).flatMap(sessaoVotacao -> {
			LocalDateTime dataAtual = LocalDateTime.now();
			if (sessaoVotacao.getFim().isBefore(dataAtual)) {
				log.error("Erro ao realizar voto para a sessão com ID: {}", idSessaoVotacao);
				return Mono.error(new BusinessException("A sessão de votação não está mais ativa."));
			} else {
				return verificarUsuarioHabilitadoVotacaoService.verificar(cpf).flatMap(usuarioHabilitado -> {
					Voto voto = criarVoto(cpf, idSessaoVotacao, opcao);
					return votoRepository.save(voto).thenReturn(VotoRespostaDto.builder().mensagem("Seu voto foi registrado com sucesso.").build());
				});
			}
		});
	}

	private Voto criarVoto(String cpf, Long idSessaoVotacao, boolean opcao) {
		return Voto.builder().cpf(cpf).sessaoVotacaoId(idSessaoVotacao).opcao(opcao).dataVoto(LocalDateTime.now()).build();
	}
}
