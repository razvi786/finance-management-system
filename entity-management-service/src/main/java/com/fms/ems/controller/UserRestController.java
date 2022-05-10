package com.fms.ems.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    final List<User> users = userRepository.findAll();
    return new ResponseEntity<List<User>>(users, HttpStatus.OK);
  }

  @PostMapping("/user")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    final User savedUser = userRepository.save(user);
    return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
  }
}
