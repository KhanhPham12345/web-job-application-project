package com.luv2code.springsecurity.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.CompanyDao;
import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.user.CompanyRecruitmentDTO;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyDao companyDao;

	@Override
	@Transactional
	public List<Company> getAllCompanies() {
		
		return companyDao.getAllCompanies();
	}
	
	@Override
	@Transactional
	public List<CompanyRecruitmentDTO> getPopularCompanies() {
	    return companyDao.getPopularCompanies();
	}

	@Override
	@Transactional
	public Company findById(Long id) {
		return companyDao.findById(id);
	}
	
	@Override
	@Transactional
	public List<Company> getCompanyByUserId(Long userId) {
        return companyDao.findByUserId(userId);
    }

	@Override
	@Transactional
	public void updateCompany(Company company) {
		companyDao.saveOrUpdateCompany(company);
	}

	@Override
	@Transactional
	public void delete(Long companyId) {
		companyDao.delete(companyId);
		
	}

	@Override
	@Transactional
	public void saveNewCompany(@Valid Company company) {
		companyDao.saveOrUpdateCompany(company);
	}
	
	
}
