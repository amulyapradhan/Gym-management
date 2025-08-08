package com.indo.GymManagement.entity;

public class ChangePasswordRequest {
	private String oldPassword;
    private String newPassword;
    private String mobileNumber;
    private String email;
	public ChangePasswordRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ChangePasswordRequest(String oldPassword, String newPassword, String mobileNumber, String email) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.mobileNumber = mobileNumber;
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
    
}
