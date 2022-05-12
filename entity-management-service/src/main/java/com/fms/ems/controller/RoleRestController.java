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

import com.fms.ems.entity.Role;
import com.fms.ems.repository.RoleRepository;

@RestController
@RequestMapping("/api")
public class RoleRestController {

	@Autowired RoleRepository roleRepository;
	
	@GetMapping("/roles")
	  public ResponseEntity<List<Role>> getAllRoles() {
	    try {
	      final List<Role> roles = roleRepository.findAll();
	      if (roles.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      } else {
	        return new ResponseEntity<>(roles, HttpStatus.OK);
	      }
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@PostMapping("/role")
	public ResponseEntity<Role> createRole(@RequestBody Role role){
		try {
			Role createdRole = roleRepository.save(role);
			return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/role/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable String id, @RequestBody Role role) {
		try {
			Optional<Role> roleToBeUpdated = roleRepository.findById(Integer.parseInt(id));
			if(roleToBeUpdated.isPresent()) {
				Role updatedRole = roleRepository.save(role);
				return new ResponseEntity<>(updatedRole, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/role/{id}")
	public ResponseEntity<Role> getRoleById(@PathVariable String id) {
		try {
			Optional<Role> searchedRole = roleRepository.findById(Integer.parseInt(id));
			if(searchedRole.isPresent()) {
				return new ResponseEntity<>(searchedRole.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/role/{id}")
	public ResponseEntity<Role> deleteRole(@PathVariable String id) {
		try {
			Optional<Role> roleToBeDeleted = roleRepository.findById(Integer.parseInt(id));
			if(roleToBeDeleted.isPresent()) {
				roleRepository.delete(roleToBeDeleted.get());
				return new ResponseEntity<>(roleToBeDeleted.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
