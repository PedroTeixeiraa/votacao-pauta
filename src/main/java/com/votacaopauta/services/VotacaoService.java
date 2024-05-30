package com.votacaopauta.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.controllers.dto.VotoRespostaDto;
import com.votacaopauta.domain.Voto;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Mono;

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
		boolean opcao = votoRequisicaoDto.getOpcao();

		return buscarSessaoVotacaoService.buscar(idSessaoVotacao).flatMap(sessaoVotacao -> {
			LocalDateTime dataAtual = LocalDateTime.now();
			if (sessaoVotacao.getFim().isBefore(dataAtual)) {
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
