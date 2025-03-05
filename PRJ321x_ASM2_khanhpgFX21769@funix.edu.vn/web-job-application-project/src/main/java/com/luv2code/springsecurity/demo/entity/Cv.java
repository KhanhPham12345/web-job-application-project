package com.luv2code.springsecurity.demo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cv")
public class Cv {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Column(name = "file_name")
    private byte[] fileLink;
    
    @Column(name = "file_name_text")
    private String fileNameText;
    
    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ApplyPost> applyPosts = new ArrayList<>();
    // other fields

    public Cv() {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getFileLink() {
		return fileLink;
	}

	public void setFileLink(byte[] file_link) {
		this.fileLink = file_link;
	}

	public String getFileNameText() {
		return fileNameText;
	}

	public void setFileNameText(String fileNameText) {
		this.fileNameText = fileNameText;
	}

	public List<ApplyPost> getApplyPosts() {
		return applyPosts;
	}

	public void setApplyPosts(List<ApplyPost> applyPosts) {
		this.applyPosts = applyPosts;
	}

}
