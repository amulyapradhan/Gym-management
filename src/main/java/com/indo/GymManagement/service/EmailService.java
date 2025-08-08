package com.indo.GymManagement.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	public void sendOtp(String email, String otp);

}
