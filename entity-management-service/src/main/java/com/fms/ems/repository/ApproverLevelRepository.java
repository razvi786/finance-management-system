package com.fms.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.ems.entity.ApproverLevel;

public interface ApproverLevelRepository extends JpaRepository<ApproverLevel, Integer> {

	List<ApproverLevel> findAllByProjectProjectIdOrderByLevel(Integer projectId);
}
