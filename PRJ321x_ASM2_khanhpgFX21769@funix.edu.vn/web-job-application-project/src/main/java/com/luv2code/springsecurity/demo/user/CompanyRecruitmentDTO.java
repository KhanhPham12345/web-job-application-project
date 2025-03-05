package com.luv2code.springsecurity.demo.user;

public class CompanyRecruitmentDTO {
    private int id;
    private String nameCompany;
    private int totalRecruitments;
    private String logo;

    public CompanyRecruitmentDTO() {
        
    }

	public CompanyRecruitmentDTO(int id, String nameCompany, int totalRecruitments, String logo) {
		this.id = id;
		this.nameCompany = nameCompany;
		this.totalRecruitments = totalRecruitments;
		this.logo = logo;
	}

	// Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public int getTotalRecruitments() {
        return totalRecruitments;
    }

    public void setTotalRecruitments(int totalRecruitments) {
        this.totalRecruitments = totalRecruitments;
    }

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
    
}
