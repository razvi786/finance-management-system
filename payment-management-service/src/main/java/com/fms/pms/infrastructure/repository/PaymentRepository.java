package com.fms.pms.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fms.pms.infrastructure.entity.Payment;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, UUID> {}
