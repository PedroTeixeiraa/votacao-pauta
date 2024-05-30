package com.votacaopauta.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.controllers.dto.VotoRespostaDto;
import com.votacaopauta.domain.Voto;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.SessaoVotacaoRepository;
import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Service
public class VotacaoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	@Autowired
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	public Mono<VotoRespostaDto> votar(VotoRequisicaoDto votoRequisicaoDto) {
		Long idSessaoVotacao = votoRequisicaoDto.getIdSessaoVotacao();
		String cpf = votoRequisicaoDto.getCpf();

		return sessaoVotacaoRepository.findById(idSessaoVotacao)
				.switchIfEmpty(Mono.error(new BusinessException("A sessão de votação não foi encontrada.")))
				.flatMap(sessaoVotacao -> {
					LocalDateTime dataAtual = LocalDateTime.now();
					if (sessaoVotacao.getFim().isBefore(dataAtual)) {
						return Mono.error(new BusinessException("A sessão de votação não está mais ativa."));
					} else {
						return verificarUsuarioHabilitadoParaVotar(cpf).flatMap(usuarioHabilitado -> {
							if (Boolean.TRUE.equals(usuarioHabilitado)) {
								Voto voto = Voto.builder()
										.cpf(cpf)
										.sessaoVotacaoId(idSessaoVotacao)
										.opcao(votoRequisicaoDto.getOpcao())
										.dataVoto(LocalDateTime.now())
										.build();

								return votoRepository.save(voto)
										.thenReturn(VotoRespostaDto.builder().mensagem("Seu voto foi registrado com sucesso.").build());
							} else {
								return Mono.error(new BusinessException(
										"Desculpe, você já participou de uma votação ativa. Por favor, aguarde o término dessa sessão para votar novamente."));
							}
						});
					}
				});
	}

	public Mono<Boolean> verificarUsuarioHabilitadoParaVotar(String cpf) {
		LocalDateTime dataAtual = LocalDateTime.now();

		return sessaoVotacaoRepository.findAll()
				.filter(sessao -> sessao.getFim().isAfter(dataAtual))
				.flatMap(sessao -> votoRepository.existsBySessaoVotacaoIdAndCpf(sessao.getId(), cpf))
				.any(hasVoted -> !hasVoted);
	}
}
