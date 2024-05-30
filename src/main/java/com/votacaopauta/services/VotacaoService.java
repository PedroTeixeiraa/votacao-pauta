package com.votacaopauta.services;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.votacaopauta.controllers.dto.VotoRequisicaoDto;
import com.votacaopauta.domain.Voto;
import com.votacaopauta.exceptions.BusinessException;
import com.votacaopauta.repositories.VotoRepository;
import reactor.core.publisher.Mono;

@Service
public class VotacaoService {

	@Autowired
	private VotoRepository votoRepository;

	@Autowired
	private SessaoVotacaoService sessaoVotacaoService;

	public Mono<Void> votar(VotoRequisicaoDto votoRequisicaoDto) {
		UUID idPauta = votoRequisicaoDto.getIdPauta();
		String cpf = votoRequisicaoDto.getCpf();

		return sessaoVotacaoService.buscarSessaoVotacaoAtiva(idPauta)
				.flatMap(sessaoVotacao -> {
					return votoRepository.usuarioHabilitadoParaVotar(sessaoVotacao.getId(), cpf)
							.flatMap(isUsuarioHabilitadoVotacao -> {
								if (Boolean.FALSE.equals(isUsuarioHabilitadoVotacao)) {
									return Mono.error(new BusinessException("Usuário não está habilitado para realizar essa votação!"));
								}

								Voto voto = Voto.builder()
										.cpf(cpf)
										.sessaoVotacaoId(sessaoVotacao.getId())
										.opcao(votoRequisicaoDto.getOpcao())
										.dataVoto(LocalDateTime.now())
										.build();

								return votoRepository.save(voto).then();
							});
				})
				.switchIfEmpty(Mono.error(new BusinessException("Não há sessão de votação ativa para esta pauta.")));
	}
}
