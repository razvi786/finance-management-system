package com.fms.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.ems.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
