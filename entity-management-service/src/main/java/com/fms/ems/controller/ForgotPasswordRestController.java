package com.fms.ems.controller;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.management.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.simpleemail.model.NotificationType;
import com.fms.ems.entity.User;
import com.fms.ems.repository.UserRepository;
import com.fms.ems.services.EmailSenderService;
import com.fms.ems.services.UserService;

@RestController
@RequestMapping("/api")
public class ForgotPasswordRestController {

	@Autowired UserRepository userRepository;
	
	@Autowired private EmailSenderService emailSenderService;
	
	@Autowired private UserService userService;
	
	@GetMapping("/forgot-password/{email}")
	  public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
	    try {
	      final Optional<User> userOptional = userRepository.findByEmail(email);
	      if (userOptional.isPresent()) {
	        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
	      } else {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	    } catch (Exception e) {
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@GetMapping("/forgot-password/{email}/verifyOTP/{otp}")
	public ResponseEntity<User> verifyOTP(@PathVariable String otp, @PathVariable String email){
		
		try {
		      final Optional<User> userOptional = userRepository.findByEmail(email);
		      if (userOptional.isPresent()) {
		    	  userOptional.get().getVerificationCode().equals(otp);
		        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
		      } else {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}
	
	@PostMapping("/forgot-password/send-email/{email}")
	@Transactional
	public ResponseEntity<String> sendVerificationCodeToUser(@PathVariable String email) {
	    try {
	    	char[] otp = userService.genarateOTP();
	    	
	      // Send Email
	      final String subject = "Verification Code";
	      final boolean isEmailSent =
	          emailSenderService.sendEmail(email, subject, "Your Verification Code is : "+otp);
	      if (isEmailSent) {
	        return new ResponseEntity<>(email, HttpStatus.OK);
	      } else {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	      }
	    } catch (Exception e) {
	      System.out.println("Some Exception Occurred: " + e);
	      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
