package com.buenoezandro.cryptography.controller.dto;

public record CreateTransactionRequest(String userDocument, String creditCardToken, Long value) {
}