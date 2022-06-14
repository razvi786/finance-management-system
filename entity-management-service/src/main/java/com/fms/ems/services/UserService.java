package com.fms.ems.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fms.ems.entity.User;
import com.fms.ems.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
    private UserRepository userRepo;
     
 
    public void updateResetPasswordToken(String token, String email) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
        	user.get().setVerificationCode(token);
        	userRepo.save(user.get());
        } else {
            log.error("User not found with email:", email);
        }
    }
    
    public User getByResetPasswordToken(String token) {
        return userRepo.findByVerificationCode(token);
    }
     
    public void updatePassword(User user, String newPassword) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(newPassword);
    	user.setPassword(newPassword);
         
    	user.setVerificationCode(null);
    	userRepo.save(user);
    }
    
    public String genarateOTP(){

//		String numbers = "0123456789";
//   	 
//        // Using random method
//        Random rndm_method = new Random();
// 
//        char[] otp = new char[4];
// 
//        for (int i = 0; i < 4; i++)
//        {
//            otp[i] =
//             numbers.charAt(rndm_method.nextInt(numbers.length()));
//        }
//        return otp;
    	int randomPin   =(int) (Math.random()*9000)+1000;
        String otp  = String.valueOf(randomPin);
        return otp;
	}
}
