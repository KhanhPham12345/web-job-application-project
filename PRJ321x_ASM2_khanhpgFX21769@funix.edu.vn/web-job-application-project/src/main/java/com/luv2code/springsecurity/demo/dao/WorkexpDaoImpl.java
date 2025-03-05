package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.WorkExperience;

@Repository
public class WorkexpDaoImpl implements WorkexpDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public WorkExperience getWorkexp(int theInt) {
		Session currentSession = sessionFactory.getCurrentSession();
		WorkExperience workexp = currentSession.get(WorkExperience.class, theInt);
		return workexp;
	}

	@Override
	public void updateWorkexp(WorkExperience workexp) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(workexp);
	}

	@Override
	public void deleteWorkexpById(int workexpId) {
		Session currentSession = sessionFactory.getCurrentSession();
		WorkExperience workexp = currentSession.get(WorkExperience.class, workexpId);
		currentSession.delete(workexp);
	}

	@Override
	public List<WorkExperience> getWorkExperienceByUserId(Long userId) {
		Session currentSession = sessionFactory.getCurrentSession();
        Query<WorkExperience> query = currentSession.createQuery("from WorkExperience where user.id = :userId", WorkExperience.class);
        query.setParameter("userId", userId);
        
        return query.getResultList();
	}
	
}
