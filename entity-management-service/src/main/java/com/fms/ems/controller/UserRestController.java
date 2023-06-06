package com.fms.ems.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ems.entity.Role;
import com.fms.ems.entity.User;
import com.fms.ems.repository.RoleRepository;
import com.fms.ems.repository.UserRepository;

@RestController
@RequestMapping("/api/ems")
@CrossOrigin(origins = "*")
public class UserRestController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			final List<User> users = userRepository.findAll();
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(users, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		try {
			final Optional<User> userOptional = userRepository.findById(Integer.parseInt(id));
			if (userOptional.isPresent()) {
				return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//  @CrossOrigin(origins = "http://localhost:4200")
//  @GetMapping("/user/{email}")
//  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
//    try {
//      final Optional<User> userOptional = userRepository.findByEmail(email);
//      if (userOptional.isPresent()) {
//        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
//      } else {
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//      }
//    } catch (Exception e) {
//      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//  }

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/user/{email}/{password}")
	public ResponseEntity<User> getUserByEmailAndPassword(@PathVariable String email, @PathVariable String password) {
		try {
			final Optional<User> userOptional = userRepository.findByEmailAndPassword(email, password);
			if (userOptional.isPresent()) {
				System.out.println("User: " + userOptional.get());
				return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/user")
	@Transactional
	public ResponseEntity<User> createUser(@RequestBody User user) {
		try {
			if (user.getRole() != null) {
				final Optional<Role> roleOptional = roleRepository.findById(user.getRole().getRoleId());
				if (roleOptional.isPresent()) {
					final Role role = roleOptional.get();
					user.setRole(role);
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}
			final User savedUser = userRepository.save(user);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user/{id}")
	@Transactional
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
		try {
			final Optional<User> userOptional = userRepository.findById(Integer.parseInt(id));
			if (userOptional.isPresent()) {
				final User updatedUser = userRepository.save(user);
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/user/{id}")
	@Transactional
	public ResponseEntity<User> deleteUser(@PathVariable String id) {
		try {
			final Optional<User> userOptional = userRepository.findById(Integer.parseInt(id));
			if (userOptional.isPresent()) {
				userRepository.delete(userOptional.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
