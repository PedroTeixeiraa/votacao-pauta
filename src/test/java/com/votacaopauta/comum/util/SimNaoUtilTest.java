package com.votacaopauta.comum.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.votacaopauta.comum.exceptions.BusinessException;

class SimNaoUtilTest {

	@Test
	void deveConverterBooleanoSim() {
		assertTrue(SimNaoUtil.converterBooleano("Sim"));
	}

	@Test
	void deveConverterBooleanoNao() {
		assertFalse(SimNaoUtil.converterBooleano("Não"));
	}

	@Test
	void deveConverterBooleanoNaoComAcento() {
		assertFalse(SimNaoUtil.converterBooleano("Nao"));
	}

	@Test
	void deveLancarExcecaoParaValorInvalido() {
		BusinessException exception = assertThrows(BusinessException.class, () -> {
			SimNaoUtil.converterBooleano("OutroValor");
		});

		assertEquals("A opção é inválida! Valores aceitos (Sim, Não)", exception.getMessage());
	}
}
