package com.buenoezandro.cryptography.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buenoezandro.cryptography.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}