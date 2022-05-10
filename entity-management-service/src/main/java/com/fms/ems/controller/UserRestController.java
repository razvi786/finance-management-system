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

import com.fms.ems.entity.User;
import com.fms.ems.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserRestController {

  @Autowired UserRepository userRepository;

  @GetMapping("/users")
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

  @PostMapping("/user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    try {
      final User savedUser = userRepository.save(user);
      return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/user/{id}")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    final User updatedUser = userRepository.save(user);
    try {
      return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/user/{id}")
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
