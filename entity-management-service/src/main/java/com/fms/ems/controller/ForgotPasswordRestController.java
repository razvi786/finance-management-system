package com.fms.ems.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fms.ems.entity.User;
import com.fms.ems.repository.UserRepository;
import com.fms.ems.services.EmailSenderService;
import com.fms.ems.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ems")
@CrossOrigin(origins = "*")
@Slf4j
public class ForgotPasswordRestController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private EmailSenderService emailSenderService;

	@Autowired
	private UserService userService;

	@CrossOrigin(origins = "http://localhost:4200")
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

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/forgot-password/{email}/verifyOTP/{otp}")
	public ResponseEntity<User> verifyOTP(@PathVariable String otp, @PathVariable String email) {

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

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/forgot-password/send-email/{email}")
	// @Transactional
	public ResponseEntity<User> sendVerificationCodeToUser(@PathVariable String email) {
		try {
			String otp = userService.genarateOTP();
			log.debug("OTP", otp);
			final Optional<User> userOptional = userRepository.findByEmail(email);
			if (userOptional.isPresent()) {
				userOptional.get().setVerificationCode(otp);
				userRepository.save(userOptional.get());
			}
			// Send Email
			final String subject = "Verification Code";
			final boolean isEmailSent = emailSenderService.sendEmail(email, subject,
					"Your Verification Code is : " + otp);
			if (isEmailSent) {
				return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			System.out.println("Some Exception Occurred: " + e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @PutMapping("/reset-password/{password}")
	// public ResponseEntity<User> updatePassword(@PathVariable String password) {
	// userService.updatePassword(null, password);
	// }
}
