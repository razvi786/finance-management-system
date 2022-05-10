package com.fms.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.ems.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {}
