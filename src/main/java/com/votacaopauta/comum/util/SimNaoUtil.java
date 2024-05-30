package com.votacaopauta.comum.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.votacaopauta.comum.exceptions.BusinessException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimNaoUtil {

	public static boolean converterBooleano(String valor) {
		if ("Sim".equalsIgnoreCase(valor)) {
			return true;
		} else if ("Não".equalsIgnoreCase(valor) || "Nao".equalsIgnoreCase(valor)) {
			return false;
		} else {
			throw new BusinessException("A opção é inválida! Valores aceitos (Sim, Não)");
		}
	}
}
