package com.fms.rms.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fms.rms.infrastructure.entity.Request;

@Repository
public interface RequestRepository extends MongoRepository<Request, UUID> {
}
