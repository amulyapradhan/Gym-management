package com.indo.GymManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indo.GymManagement.entity.ChangePasswordRequest;
import com.indo.GymManagement.entity.OtpRequest;
import com.indo.GymManagement.entity.RegisterCustomerRequest;
import com.indo.GymManagement.entity.User;
import com.indo.GymManagement.serviceimpl.EmailServiceImpl;
import com.indo.GymManagement.serviceimpl.SmsService;
import com.indo.GymManagement.serviceimpl.UserServiceImpl;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserServiceImpl userService;
    
	@Autowired
    private EmailServiceImpl emailService; 
	
	@Autowired
	private SmsService smsService;
	 
	@Value("${file.upload-dir}") 
	private String uploadDir;
	
	// Temporary storage for OTPs
	private  List<String> otpStorage = new ArrayList<>();
	
	private  List<String> mobileotpStorage = new ArrayList<>();
	
	private  List<String> forgetotpStorage = new ArrayList<>();


	 @PostMapping("/uploadImage")
	    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image, @RequestParam("email") String email) {
	        try {
	            if (image.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select an image file.");
	            }
	            // Get the original file name
	            String originalFileName = image.getOriginalFilename();
	            if (originalFileName == null) {
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid file name.");
	            }
	            // Sanitize the file name to remove any path elements
	            String fileName = originalFileName.replaceAll("[^a-zA-Z0-9.-]", "_");
	            // Get the upload directory path
	            Path path = Paths.get(uploadDir);
	            // Ensure directory exists
	            if (!Files.exists(path)) {
	                Files.createDirectories(path);
	            }
	            // Save the file to the server
	            File targetFile = path.resolve(fileName).toFile();
	            image.transferTo(targetFile);

	            // Associate the uploaded image with the user in the database
	            userService.updateUserImage(email, fileName);

	            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");
	        } catch (IOException e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("An error occurred while uploading the image: " + e.getMessage());
	        }
	    }
	   
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterCustomerRequest request) {
        try {
            
			// Handle registration with the provided user data
            User savedUser = userService.saveUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userService.validateUser(loginRequest.getEmail(), loginRequest.getPassword());
        
        if (user.isPresent()) {
            // Return a JSON response with a success message and any other data you might want to include
            return ResponseEntity.ok(Collections.singletonMap("message", "Login successful"));
        } else {
            // Return a JSON response with an error message
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid email or password"));
        }
    }
    
    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterCustomerRequest request) {
        try {
            // Handle registration with the provided user data
            User savedUser = userService.saveAdminDetails(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user: " + e.getMessage());
        }
    }
    
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody String email) {
    	// Remove double quotes from the email string
        email = email.replace("\"", "").trim();
        User user = userService.findByEmailAndRole(email, "Admin");
        if (user == null) {
            return ResponseEntity.status(404).body("Email not found or not an admin.");
        }
        // Generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
       // Store OTP in the otpStorage map with the email as the key
        otpStorage.add(otp);
        System.out.println("Stored OTP: " + otp + " for email: " + email.toLowerCase());
        try {
            emailService.sendOtp(email, otp);
            return ResponseEntity.ok("OTP sent to email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {        
        String enteredOtp = otpRequest.getOtp();
        String storedOtp = otpStorage.get(0);
        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            otpStorage.remove(0);
            response.put("message", "OTP verified.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid OTP.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    } 
    
    @GetMapping("/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = userService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/{clientId}")
    public ResponseEntity<User> getMembership(@PathVariable String clientId) {
        Optional<User> membership = userService.getMembershipByClientId(clientId);

        return membership.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/by-email")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.getUserByEmail(email);     
        // Check if user is present and return appropriate response
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/membership")
    public ResponseEntity<User> getMembershipByEmail(@RequestParam String email) {
        Optional<User> membership = userService.getUserByEmail(email);
        return membership.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
        
    @PostMapping("/send-mobile-otp")
    public ResponseEntity<?> sendMobileOtp(@RequestBody Map<String, String> request) {
    	String mobileNumber = request.get("mobileNumber");
        User user = userService.findByMobileNumber(mobileNumber);        
        if (user == null) {
            return ResponseEntity.status(404).body("Mobile number not found.");
        }
        // Generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));        
        // Store OTP in the otpStorage map with the mobile number as the key
        mobileotpStorage.add(otp);
        System.out.println("Stored OTP: " + otp + " for mobile number: " + mobileNumber);
        try {
            smsService.sendOtp(mobileNumber, otp); // Assuming smsService is used to send SMS
            return ResponseEntity.ok("OTP sent to mobile number.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-mobile-otp")
    public ResponseEntity<?> verifyMobileOtp(@RequestBody OtpRequest otpRequest) {        
        String enteredOtp = otpRequest.getOtp();
        String storedOtp = mobileotpStorage.get(0);

        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
        	mobileotpStorage.remove(0);
            response.put("message", "OTP verified.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid OTP.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        // Extract the request details
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String mobileNumber = request.getMobileNumber();
        // Find user by mobile number
        User user = userService.findByMobileNumber(mobileNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        // Verify old password
        if (!userService.verifyPassword(user, oldPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password.");
        }
        // Update the password
        userService.updatePassword(user, newPassword);
        return ResponseEntity.ok("Password updated successfully.");
    } 
    
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required");
        }

        Optional<User> user = userService.getUserByEmail(email);
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String token = UUID.randomUUID().toString();
        userService.savePasswordResetToken(user.get(), token);

        String resetLink = "http://localhost:3000/forget-password?token=" + token;
        emailService.sendPasswordResetEmail(user.get().getEmail(), resetLink);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Password reset link sent"));
    }


    @PostMapping("/send-forget-otp")
    public ResponseEntity<?> sendForgetOtp(@RequestBody String email) {
        email = email.replace("\"", "").trim();
        Optional<User> userOptional = userService.getUserByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Email not found."));
        }

        // Generate OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        forgetotpStorage.add( otp);
        System.out.println("Stored OTP: " + otp + " for email: " + email);

        try {
            emailService.sendOtp(email, otp);
            return ResponseEntity.ok(Collections.singletonMap("message", "OTP sent to email."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Failed to send OTP: " + e.getMessage()));
        }
    }


    
    @PostMapping("/verify-forget-otp")
    public ResponseEntity<?> verifyForgetOtp(@RequestBody Map<String, String> otpRequest) {
        String enteredOtp = otpRequest.get("otp");

        String storedOtp = forgetotpStorage.get(0);
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            forgetotpStorage.remove(0);
            return ResponseEntity.ok(Collections.singletonMap("message", "OTP verified."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Invalid OTP."));
        }
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ChangePasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();
        String newPassword = resetPasswordRequest.getNewPassword();

        Optional<User> userOptional = userService.getUserByEmail(email);

        if (!userOptional.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                 .body(Collections.singletonMap("message", "User not found."));
        }

        User user = userOptional.get();
        userService.updatePassword(user, newPassword);

        return ResponseEntity.ok(Collections.singletonMap("message", "Password has been reset successfully."));
    }


}


