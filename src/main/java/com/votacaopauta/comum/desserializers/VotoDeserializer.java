package com.votacaopauta.comum.desserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.votacaopauta.comum.exceptions.BusinessException;

public class VotoDeserializer extends JsonDeserializer<Boolean> {

	@Override
	public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
		String valor = jsonParser.getText();
		if ("Sim".equalsIgnoreCase(valor)) {
			return true;
		} else if ("Não".equalsIgnoreCase(valor) || "Nao".equalsIgnoreCase(valor)) {
			return false;
		} else {
			throw new BusinessException("A opção de voto é inválida! Valores aceitos (Sim, Não)");
		}
	}
}
