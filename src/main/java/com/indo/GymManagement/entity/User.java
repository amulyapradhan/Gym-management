package com.indo.GymManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Common fields for both customers and admins
    private String name;
    private String mobileNumber;
    private String address;

    // Customer-specific fields
    private Integer age;
    private Double weight;
    private String gender;

    @Lob
    private byte[] profilePic;
    // Admin-specific field
    private String role;
    
 // Profile picture path
    private String profilePicPath;

    // Membership details
    private String membershipType;
    private String membershipStartDate;
    private String membershipEndDate;
    private String clientId;
    
    
    

    public User() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	public User(Long id, String email, String password, String name, String mobileNumber, String address, Integer age,
			Double weight, String gender, byte[] profilePic, String role, String profilePicPath, String membershipType,
			String membershipStartDate, String membershipEndDate, String clientId) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.age = age;
		this.weight = weight;
		this.gender = gender;
		this.profilePic = profilePic;
		this.role = role;
		this.profilePicPath = profilePicPath;
		this.membershipType = membershipType;
		this.membershipStartDate = membershipStartDate;
		this.membershipEndDate = membershipEndDate;
		this.clientId = clientId;
	}

	// Getters and Setters
	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(String membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public String getMembershipEndDate() {
        return membershipEndDate;
    }

    public void setMembershipEndDate(String membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
    }

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}
	 public byte[] getProfilePic() {
	        return profilePic;
	    }

	    public void setProfilePic(byte[] profilePic) {
	        this.profilePic = profilePic;
	    }

		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}
    
	    
}

