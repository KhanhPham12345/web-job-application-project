package com.luv2code.springsecurity.demo.service;

import java.util.List;

import javax.validation.Valid;

import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.user.CompanyRecruitmentDTO;

public interface CompanyService {
	
	public List<Company> getAllCompanies();
	
	public List<CompanyRecruitmentDTO> getPopularCompanies();

	public Company findById(Long id);

	List<Company> getCompanyByUserId(Long userId);

	public void updateCompany(Company company);

	public void delete(Long companyId);

	public void saveNewCompany(@Valid Company company);
	
}
