package com.indo.GymManagement.dto;

import lombok.Data;

@Data
public class OtpRequest {

	private String emailOrPhone;
    private String otp;
}
