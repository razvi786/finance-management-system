package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ems.entity.Vendor;
import com.fms.ems.repository.VendorRepository;

@RestController
@RequestMapping("/api")
public class VendorRestController {

  @Autowired VendorRepository vendorRepository;

  @GetMapping("/vendors")
  public ResponseEntity<List<Vendor>> getAllVendors() {
    try {
      final List<Vendor> vendors = vendorRepository.findAll();
      if (vendors.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
        return new ResponseEntity<>(vendors, HttpStatus.OK);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/vendor/{id}")
  public ResponseEntity<Vendor> getVendorById(@PathVariable String id) {
    try {
      final Optional<Vendor> vendorOptional = vendorRepository.findById(Integer.parseInt(id));
      if (vendorOptional.isPresent()) {
        return new ResponseEntity<>(vendorOptional.get(), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/vendor")
  @Transactional
  public ResponseEntity<Vendor> createVendor(@RequestBody Vendor vendor) {
    try {
      final Vendor savedVendor = vendorRepository.save(vendor);
      return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/vendor/{id}")
  @Transactional
  public ResponseEntity<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
    try {
      final Optional<Vendor> vendorOptional = vendorRepository.findById(Integer.parseInt(id));
      if (vendorOptional.isPresent()) {
        final Vendor updatedVendor = vendorRepository.save(vendor);
        return new ResponseEntity<>(updatedVendor, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/vendor/{id}")
  @Transactional
  public ResponseEntity<Vendor> deleteVendor(@PathVariable String id) {
    try {
      final Optional<Vendor> vendorOptional = vendorRepository.findById(Integer.parseInt(id));
      if (vendorOptional.isPresent()) {
        vendorRepository.delete(vendorOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
