package com.fms.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fms.ems.entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {}
