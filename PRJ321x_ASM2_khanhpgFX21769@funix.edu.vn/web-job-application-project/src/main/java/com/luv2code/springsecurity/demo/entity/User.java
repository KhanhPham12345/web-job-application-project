package com.luv2code.springsecurity.demo.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "username")
    private String userName;
    
    @Email(message = "Invalid email format")
    @NotBlank(message = "Company email is required")
    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\d+$", message = "Phone number must contain only numbers")
    private String phoneNumber;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
        name = "user_role", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cv> cvs;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WorkExperience> workExperiences;

    // Default constructor
    public User() {
    }

    // Constructor with essential fields
    public User(String password, String userName, String email) {
        this.password = password;
        this.userName = userName;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Cv> getCvs() {
        return cvs;
    }

    public void setCvs(List<Cv> cvs) {
        this.cvs = cvs;
    }

    public List<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }
    
	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", fullName=" + fullName + ", email=" + email
				+ ", address=" + address + ", description=" + description + ", phoneNumber=" + phoneNumber + ", image="
				+ image + ", status=" + status + ", roles=" + roles + ", cvs=" + cvs + "]";
	}
}
