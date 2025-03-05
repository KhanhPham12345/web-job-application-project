package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.RecruitmentDao;
import com.luv2code.springsecurity.demo.entity.Recruitment;

@Service
public class RecruitmentServiceImpl implements RecruitmentService{
	
	@Autowired
	private RecruitmentDao recruitmentDAO;

	@Override
	@Transactional
	public void saveRecruitment(Recruitment recruitment) {
		
		recruitmentDAO.saveRecruitment(recruitment);
	}

	@Override
	@Transactional
	public List<Recruitment> getPopularRecruitments() {
		return recruitmentDAO.getPopularRecruitment();
	}

	@Override
	@Transactional
	public List<Object[]> getCategoriesBasedonRecruitment() {
		
		return recruitmentDAO.getCategoriesBasedonRecruitment();
	}

	@Override
	@Transactional
	public List<Recruitment> getRecruitmentBasedsOnUserId(Long userId) {
		// TODO Auto-generated method stub
		return recruitmentDAO.getRecruitmentBasedsOnUserId(userId);
	}

	@Override
	@Transactional
	public Recruitment getRecruitment(int id) {
		return recruitmentDAO.getRecruitment(id);
	}

	@Override
	@Transactional
	public void deleteRecruitment(int theId) {
		recruitmentDAO.deleteRecruitment(theId);
	}

	@Override
	@Transactional
	public List<Recruitment> findByCompanyId(Long companyId) {
		return recruitmentDAO.findByCompanyId(companyId);
	}

	@Override
	@Transactional
	public List<Recruitment> findAllRecruitments() {
		return recruitmentDAO.getAllRecruitments();
	}

	@Override
	@Transactional
	public List<Recruitment> searchRecruitments(String theSearchName) {
		// TODO Auto-generated method stub
		return recruitmentDAO.searchRecruitments(theSearchName);
	}

	@Override
	@Transactional
	public List<Recruitment> getRecruitments(int pageSize) {
		return recruitmentDAO.getRecruitmentBasedOnPage(pageSize);
	}

	@Override
	@Transactional
	public List<Recruitment> searchRecruitmentsHrid(String theSearchName, Long userId) {
		return recruitmentDAO.searchRecruitmentsHrid(theSearchName,userId);
	}
	
	@Override
	@Transactional
	public List<Recruitment> getRecruitmentsHrid(int pagesize, Long userId) {
		return recruitmentDAO.getRecruitmentforHRBasedOnPage(pagesize, userId);
	}

}
