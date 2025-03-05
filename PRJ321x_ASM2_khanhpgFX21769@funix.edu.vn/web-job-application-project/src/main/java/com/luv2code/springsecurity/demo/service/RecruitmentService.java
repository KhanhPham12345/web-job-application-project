package com.luv2code.springsecurity.demo.service;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Recruitment;

public interface RecruitmentService {
	List<Recruitment> getPopularRecruitments();
	
	List<Object[]> getCategoriesBasedonRecruitment();
	
	public void saveRecruitment(Recruitment recruitment);

	List<Recruitment> getRecruitmentBasedsOnUserId(Long userId);

	public Recruitment getRecruitment(int id);

	public void deleteRecruitment(int theId);

	List<Recruitment> findByCompanyId(Long companyId);

	List<Recruitment> findAllRecruitments();

	List<Recruitment> searchRecruitments(String theSearchName);

	List<Recruitment> getRecruitments(int pageSize);

	List<Recruitment> searchRecruitmentsHrid(String theSearchName, Long userId);

	List<Recruitment> getRecruitmentsHrid(int pagesize, Long userId);
}
