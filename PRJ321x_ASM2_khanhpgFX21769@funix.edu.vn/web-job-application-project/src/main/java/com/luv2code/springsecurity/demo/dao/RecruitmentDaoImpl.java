package com.luv2code.springsecurity.demo.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Recruitment;

@Repository
public class RecruitmentDaoImpl implements RecruitmentDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Recruitment> getPopularRecruitment() {

		Session currentSession = sessionFactory.getCurrentSession();

		String hql = "FROM Recruitment r ORDER BY r.quantity DESC";

		Query<Recruitment> query = currentSession.createQuery(hql, Recruitment.class);
		
		query.setMaxResults(5);

		List<Recruitment> popularRecruitments = query.getResultList();

		return popularRecruitments;
	}

	@Override
	public List<Object[]> getCategoriesBasedonRecruitment() {

		Session currentSession = sessionFactory.getCurrentSession();

		String hql = "SELECT c.category, SUM(r.quantity) AS total_quantity FROM Recruitment r JOIN Category c ON r.category_id = c.id GROUP BY c.category ORDER BY total_quantity DESC";

		Query<Object[]> query = currentSession.createNativeQuery(hql);
		
		query.setMaxResults(4);

		List<Object[]> popularCategories = query.getResultList();

		return popularCategories;
	}

	@Override
	public List<Recruitment> getRecruitmentBasedsOnUserId(Long userId) {
		Session entityManager = sessionFactory.getCurrentSession();
		return entityManager.createQuery("SELECT r FROM Recruitment r WHERE r.user.id = :userId", Recruitment.class)
				.setParameter("userId", userId).getResultList();
	}

	@Override
	public void saveRecruitment(Recruitment recruitment) {
		Session currentSession = sessionFactory.getCurrentSession();

		currentSession.saveOrUpdate(recruitment);
	}

	@Override
	public Recruitment getRecruitment(int id) {
		Session currentSession = sessionFactory.getCurrentSession();

		// Retrieve the Recruitment entity by ID using the get method
		Recruitment recruitment = currentSession.get(Recruitment.class, id);

		return recruitment;
	}

	@Override
	public void deleteRecruitment(int id) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// delete object with primary key
		Query<?> theQuery = currentSession.createQuery("delete from Recruitment where id=:Id");

		theQuery.setParameter("Id", id);

		theQuery.executeUpdate();

	}

	@Override
	public List<Recruitment> findByCompanyId(Long companyId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
	    Query<Recruitment> theQuery = currentSession.createQuery("SELECT r FROM Recruitment r WHERE r.company.id = :companyId", Recruitment.class);
	    
		theQuery.setParameter("companyId", companyId);
		
		return theQuery.getResultList();
	}

	@Override
	public List<Recruitment> getAllRecruitments() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Recruitment> allRecruitments = currentSession.createQuery("from Recruitment ", Recruitment.class);
		
		return allRecruitments.getResultList();
	}

	@Override
	public List<Recruitment> searchRecruitments(String theSearchName) {
		
		Session currentSession = sessionFactory.getCurrentSession();

        Query<Recruitment> query = currentSession.createQuery(
                "from Recruitment r where lower(r.title) like :searchName or " +
                "lower(r.company.nameCompany) like :searchName or " +
                "lower(r.address) like :searchName or " +
                "lower(r.description) like :searchName", Recruitment.class
        );

        query.setParameter("searchName", "%" + theSearchName.toLowerCase() + "%");

        return query.getResultList();
	}

	@Override
	public List<Recruitment> getRecruitmentBasedOnPage(int pageSize) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Recruitment> theQuery = currentSession.createQuery("from Recruitment", Recruitment.class);
		theQuery.setMaxResults(pageSize);
		List<Recruitment> recruitments = theQuery.getResultList();
		return recruitments;
	}

	@Override
	public List<Recruitment> searchRecruitmentsHrid(String theSearchName, Long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		try {
	        if (theSearchName == null || theSearchName.isEmpty()) {
	            return currentSession.createQuery("from Recruitment r where r.user.id = :userId", Recruitment.class)
	                    .setParameter("userId", userId)
	                    .getResultList();
	        }
	    
	        Query<Recruitment> query = currentSession.createQuery(
	                "from Recruitment r where r.user.id = :userId and (" + 
	                "lower(r.company.nameCompany) like :searchName or " +
	                "lower(r.title) like :searchName or " +
	                "lower(r.address) like :searchName or " +
	                "lower(r.description) like :searchName)", Recruitment.class
	        );
	        
	        query.setParameter("userId", userId);
	        query.setParameter("searchName", "%" + theSearchName.toLowerCase() + "%");
	                
	        return query.getResultList();
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to fetch recruitment records", e);
	    }
	}
	
	@Override
	public List<Recruitment> getRecruitmentforHRBasedOnPage(int pageSize, Long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Recruitment> theQuery = currentSession.createQuery("from Recruitment r where r.user.id = :userId", Recruitment.class).setParameter("userId", userId);
		theQuery.setMaxResults(pageSize);
		List<Recruitment> recruitments = theQuery.getResultList();
		return recruitments;
	}

}
