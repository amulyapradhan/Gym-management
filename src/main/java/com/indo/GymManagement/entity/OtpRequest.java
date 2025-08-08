package com.indo.GymManagement.entity;

public class OtpRequest {
    private String email;
    private String otp;
    private String mobileNumber;
    
    public OtpRequest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public OtpRequest(String email, String otp, String mobileNumber) {
		super();
		this.email = email;
		this.otp = otp;
		this.mobileNumber = mobileNumber;
	}


	// Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
    
}
