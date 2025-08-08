package com.indo.GymManagement.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.indo.GymManagement.entity.RegisterCustomerRequest;
import com.indo.GymManagement.entity.User;


@Service
public interface UserService {
	public User saveUser(RegisterCustomerRequest user);
	public Optional<User> validateUser(String email, String password);
	public User saveAdminDetails(RegisterCustomerRequest request);
}
