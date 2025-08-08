package com.indo.GymManagement.serviceimpl;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.indo.GymManagement.entity.RegisterCustomerRequest;
import com.indo.GymManagement.entity.User;
import com.indo.GymManagement.repository.UserRepository;
import com.indo.GymManagement.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

   @Autowired
	private UserRepository userRepo;
	
	
	public User saveUser(RegisterCustomerRequest request) {
		User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobile());
        user.setAddress(request.getAddress());
        user.setPassword(request.getPassword());
        user.setAge(request.getAge());
        user.setWeight(request.getWeight());
        user.setGender(request.getGender());        
		return userRepo.save(user);
	}


	public Optional<User> validateUser(String email, String password) {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }


	public User saveAdminDetails(RegisterCustomerRequest request) {
		User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        return userRepo.save(user);
		
	}


	public User findByEmailAndRole(String email, String role) {
		return userRepo.findByEmailAndRole(email, role);
	}


	public List<User> getAllCustomers() {
		return userRepo.findAll();
	}


	public Optional<User> getMembershipByClientId(String clientId) {
		return userRepo.findByClientId(clientId);
	}


	public Optional<User> getUserByEmail(String email) {
		return userRepo.findByEmail(email);       
	}


	public boolean validateOtp(String mobileNumber, String otp) {
		return false;
	}


	public User findByMobileNumber(String mobileNumber) {		
		return userRepo.findByMobileNumber(mobileNumber);
	}


	public boolean verifyPassword(User user, String oldPassword) {
		String storedPassword=user.getPassword();
		return storedPassword.equals(oldPassword);
	}


	public void updatePassword(User user, String newPassword) {
		user.setPassword(newPassword);
		userRepo.save(user);
	}

	    @Transactional
	    public void updateUserImage(String email, String fileName) {
	        // Find the user by email
	    	Optional<User> optionalUser = userRepo.findByEmail(email);
	        
	        if (optionalUser.isEmpty()) {
	            throw new IllegalArgumentException("User not found with email: " + email);
	        }

	        // Get the User object from the Optional
	        User user = optionalUser.get();

	        // Update the user's image file name
	        user.setProfilePicPath(fileName);

	        // Save the updated user back to the database
	        userRepo.save(user);


	
	    }


		public void savePasswordResetToken(User user, String token) {
			// TODO Auto-generated method stub
			
		}
}
