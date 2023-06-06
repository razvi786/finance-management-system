package com.fms.ems.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.ems.entity.Vendor;
import com.fms.ems.repository.VendorRepository;

@Service
public class VendorService {

	@Autowired
	private VendorRepository vendorRepo;

	public String getVendorNameByVendorId(int vendorId) {
		Optional<Vendor> vendorOptional = vendorRepo.findById(vendorId);
		if (vendorOptional.isPresent()) {
			return vendorOptional.get().getVendorName();
		} else {
			return "";
		}
	}

}
