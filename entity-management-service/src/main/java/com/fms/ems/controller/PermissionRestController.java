package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

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

import com.fms.ems.entity.Permission;
import com.fms.ems.repository.PermissionRepository;

@RestController
@RequestMapping("/api")
public class PermissionRestController {

	@Autowired
	PermissionRepository permissionRepository;

	@GetMapping("/permissions")
	public ResponseEntity<List<Permission>> getAllPermissions() {
		try {
			final List<Permission> permissions = permissionRepository.findAll();
			if (permissions.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(permissions, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/permission")
	public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
		try {
			Permission createdPermission = permissionRepository.save(permission);
			return new ResponseEntity<>(createdPermission, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/permission/{id}")
	public ResponseEntity<Permission> updatePermission(@PathVariable String id,
			@RequestBody Permission permission) {
		try {
			Optional<Permission> permissionToBeUpdated = permissionRepository.findById(Integer.parseInt(id));
			if (permissionToBeUpdated.isPresent()) {
				Permission updatedPermission = permissionRepository.save(permission);
				return new ResponseEntity<>(updatedPermission, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/permission/{id}")
	public ResponseEntity<Permission> getPermissionById(@PathVariable String id) {
		try {
			Optional<Permission> searchedPermission = permissionRepository.findById(Integer.parseInt(id));
			if (searchedPermission.isPresent()) {
				return new ResponseEntity<>(searchedPermission.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/permission/{id}")
	public ResponseEntity<Permission> deletePermission(@PathVariable String id) {
		try {
			Optional<Permission> permissionToBeDeleted = permissionRepository.findById(Integer.parseInt(id));
			if (permissionToBeDeleted.isPresent()) {
				permissionRepository.delete(permissionToBeDeleted.get());
				return new ResponseEntity<>(permissionToBeDeleted.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
