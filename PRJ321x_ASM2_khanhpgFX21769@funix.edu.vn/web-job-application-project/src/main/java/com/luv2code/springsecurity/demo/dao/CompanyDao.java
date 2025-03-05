package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.user.CompanyRecruitmentDTO;

public interface CompanyDao {
	public List<Company> getAllCompanies();
	
	public List<CompanyRecruitmentDTO> getPopularCompanies();

	public Company findById(Long id);

	public List<Company> findByUserId(Long userId);

	public void saveOrUpdateCompany(Company company);

	public void delete(Long companyId);
	
}
