package com.luv2code.springsecurity.demo.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.user.CompanyRecruitmentDTO;

@Repository
public class CompanyDaoImpl implements CompanyDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Company> getAllCompanies() {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Company> theQuery = currentSession.createQuery("from Company", Company.class);

		List<Company> companies = theQuery.getResultList();

		return companies;
	}
	
	@Override
	public List<CompanyRecruitmentDTO> getPopularCompanies() {
	    Session currentSession = sessionFactory.getCurrentSession();

	    String sql = "SELECT c.id, c.name_company, COUNT(r.id) AS total_recruitments, c.logo " +
                "FROM Company c " +
                "LEFT JOIN Recruitment r ON c.id = r.company_id " +
                "GROUP BY c.id, c.name_company, c.logo " +  
                "HAVING COUNT(r.id) > 0 " +
                "ORDER BY total_recruitments DESC";

	    // Create a native query to get results directly into DTO
	    Query<Object[]> theQuery = currentSession.createNativeQuery(sql);
	    List<Object[]> results = theQuery.getResultList();

	    List<CompanyRecruitmentDTO> popularCompanies = new ArrayList<>();
	    
	    // Iterate over results and populate DTO
	    for (Object[] row : results) {
	        int id = ((Number) row[0]).intValue(); // Cast to Number then to int
	        String nameCompany = (String) row[1];
	        int totalRecruitments = ((Number) row[2]).intValue();
	        String logo = (String) row[3];

	        // Create and add a new CompanyRecruitmentDTO instance
	        CompanyRecruitmentDTO dto = new CompanyRecruitmentDTO(id, nameCompany, totalRecruitments, logo);
	        dto.setId(id);
	        dto.setNameCompany(nameCompany);
	        dto.setTotalRecruitments(totalRecruitments);
	        dto.setLogo(logo);

	        popularCompanies.add(dto);
	    }

	    return popularCompanies;
	}

	@Override
	public Company findById(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();

        // Create query to find the company by id
        Query<Company> theQuery = currentSession.createQuery("from Company where id=:companyId", Company.class);
        theQuery.setParameter("companyId", id);
        
        // Get the company (or null if not found)
        Company company = theQuery.uniqueResult();  // Use uniqueResult for a single entity
        
        return company;
	}

	@Override
	public List<Company> findByUserId(Long userId) {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		String hql = "SELECT c FROM Company c JOIN Recruitment r ON c.id = r.company.id WHERE r.user.id = :userId";
		
		Query<Company> theQuery = currentSession.createQuery(hql, Company.class);
        theQuery.setParameter("userId", userId);
        
        // Using a Set to remove duplicates
        Set<Company> uniqueCompanies = new LinkedHashSet<>(theQuery.getResultList());

        return new ArrayList<>(uniqueCompanies);
	}

	@Override
	public void saveOrUpdateCompany(Company company) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(company);
	}

	@Override
	public void delete(Long companyId) {
		Session currentSession = sessionFactory.getCurrentSession();
		//delete the company
		Query<?> query = currentSession.createQuery("delete from Company where id = :companyId");
		query.setParameter("companyId", companyId);
		query.executeUpdate();
	}
}
