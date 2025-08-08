package com.indo.GymManagement.entity;

import org.springframework.web.multipart.MultipartFile;

public class RegisterCustomerRequest {

    private String name;
    private String email;
    private String mobile;
    private String address;
    private String password;
    private Integer age;
    private Double weight;
    private String gender;
    private String role;
    private MultipartFile image;
    
    
	public RegisterCustomerRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public RegisterCustomerRequest(String name, String email, String mobile, String address, String password,
			Integer age, Double weight, String gender, String role, MultipartFile image) {
		super();
		this.name = name;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.password = password;
		this.age = age;
		this.weight = weight;
		this.gender = gender;
		this.role = role;
		this.image = image;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}

    
    
}
