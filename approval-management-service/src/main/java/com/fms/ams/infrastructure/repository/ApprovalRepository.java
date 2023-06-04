package com.fms.ams.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fms.ams.infrastructure.entity.Approval;

@Repository
public interface ApprovalRepository extends MongoRepository<Approval, UUID> {

	List<Approval> findAllByRequestUuid(UUID requestUuid);

}
