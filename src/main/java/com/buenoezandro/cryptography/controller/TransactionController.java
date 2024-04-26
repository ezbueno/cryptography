package com.buenoezandro.cryptography.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buenoezandro.cryptography.controller.dto.CreateTransactionRequest;
import com.buenoezandro.cryptography.controller.dto.TransactionResponse;
import com.buenoezandro.cryptography.controller.dto.UpdateTransactionRequest;
import com.buenoezandro.cryptography.service.TransactionService;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {
	private final TransactionService transactionService;

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping
	public ResponseEntity<Void> create(@RequestBody CreateTransactionRequest request) {
		this.transactionService.create(request);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<Page<TransactionResponse>> listAll(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
		var body = this.transactionService.listAll(page, pageSize);
		return ResponseEntity.ok(body);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<TransactionResponse> findById(@PathVariable(value = "id") Long id) {
		var body = this.transactionService.findById(id);
		return ResponseEntity.ok(body);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@PathVariable(value = "id") Long id,
			@RequestBody UpdateTransactionRequest request) {
		this.transactionService.update(id, request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable(value = "id") Long id) {
		this.transactionService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}