package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Recruitment;

public interface RecruitmentDao {
	
	List<Object[]> getCategoriesBasedonRecruitment();

	public List<Recruitment> getPopularRecruitment();

	List<Recruitment> getRecruitmentBasedsOnUserId(Long userId);
	
	public void saveRecruitment(Recruitment recruitment);

	public Recruitment getRecruitment(int id);
	
	public void deleteRecruitment(int id);

	List<Recruitment> findByCompanyId(Long companyId);

	List<Recruitment> getAllRecruitments();

	List<Recruitment> searchRecruitments(String theSearchName);

	List<Recruitment> getRecruitmentBasedOnPage(int pageSize);

	List<Recruitment> searchRecruitmentsHrid(String theSearchName, Long userId);

	List<Recruitment> getRecruitmentforHRBasedOnPage(int pageSize, Long userId);
}
