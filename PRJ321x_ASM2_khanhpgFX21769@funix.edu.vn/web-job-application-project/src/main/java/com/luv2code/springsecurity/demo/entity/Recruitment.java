package com.luv2code.springsecurity.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recruitment")
public class Recruitment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @NotBlank(message = "Address is mandatory")
    @Column(name = "address")
    private String address;
    
    @NotBlank(message = "Creation date is mandatory")
    @Column(name = "created_at")
    private String createdAt;
    
    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    @Column(name = "description")
    private String description;
    
    @NotBlank(message = "Experience not mandatory")
    @Column(name = "experience")
    private String experience;
    
    @NotNull(message = "Quantity not mandatory")
    @Column(name = "quantity")
    private int quantity;
    
    @NotBlank(message = "Rank is mandatory")
    @Column(name = "`rank`")
    private String rank;
    
    @NotNull(message = "Salary is mandatory")
    @Column(name = "salary")
    @Min(value = 0, message = "Salary must be a positive number")
    private int salary;
    
    @NotNull(message = "Status is mandatory")
    @Column(name = "status")
    private int status;
    
    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;
    
    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    private String type;
    
    @Column(name = "view")
    private int view;
    
    @NotBlank(message = "Deadline is mandatory")
    @Column(name = "deadline")
    private String deadline;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is mandatory")
    private Category category;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "company_id")
    @NotNull(message = "Company is mandatory")
    private Company company;

    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @NotNull(message = "User'id is mandatory")
    private User user;
    
    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApplyPost> applyPosts;

    public Recruitment() {}

    // Getters and Setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public List<ApplyPost> getApplyPosts() {
        return applyPosts;
    }

    public void setApplyPosts(List<ApplyPost> applyPosts) {
        this.applyPosts = applyPosts;
    }


	@Override
    public String toString() {
        return "Recruitment [id=" + id + ", address=" + address + ", createdAt=" + createdAt + ", description="
                + description + ", experience=" + experience + ", quantity=" + quantity + ", rank=" + rank + ", salary="
                + salary + ", status=" + status + ", title=" + title + ", type=" + type + ", view=" + view
                + ", deadline=" + deadline + ", category=" + category + ", company=" + company + ", user=" + user + "]";
    }
}
