package com.buenoezandro.cryptography.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.buenoezandro.cryptography.controller.dto.CreateTransactionRequest;
import com.buenoezandro.cryptography.controller.dto.TransactionResponse;
import com.buenoezandro.cryptography.controller.dto.UpdateTransactionRequest;
import com.buenoezandro.cryptography.entity.TransactionEntity;
import com.buenoezandro.cryptography.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void create(CreateTransactionRequest request) {
		var entity = new TransactionEntity();
		entity.setRawUserDocument(request.userDocument());
		entity.setRawCreditCardToken(request.creditCardToken());
		entity.setTransactionValue(request.value());

		this.transactionRepository.save(entity);
	}

	public Page<TransactionResponse> listAll(int page, int pageSize) {
		var content = this.transactionRepository.findAll(PageRequest.of(page, pageSize));
		return content.map(TransactionResponse::fromEntity);
	}

	public TransactionResponse findById(Long id) {
		var entity = this.transactionRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return TransactionResponse.fromEntity(entity);
	}

	public void update(Long id, UpdateTransactionRequest request) {
		var entity = this.transactionRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		entity.setTransactionValue(request.value());
		this.transactionRepository.save(entity);
	}

	public void deleteById(Long id) {
		var entity = this.transactionRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		this.transactionRepository.delete(entity);
	}
}