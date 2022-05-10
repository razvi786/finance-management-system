package com.fms.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.ems.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {}
