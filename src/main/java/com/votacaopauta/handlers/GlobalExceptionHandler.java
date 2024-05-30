package com.votacaopauta.handlers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.votacaopauta.exceptions.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(WebExchangeBindException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(WebExchangeBindException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Map<String, String>> handleBusinessExceptions(BusinessException ex) {
		Map<String, String> response = new HashMap<>();
		response.put("mensagem", ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDefinitionException.class)
	public ResponseEntity<Map<String, String>> handleInvalidDefinitionException(InvalidDefinitionException ex) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("erro", "Erro de serialização ou desserialização");
		errorResponse.put("mensagem", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}
}
