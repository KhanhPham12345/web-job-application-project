package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Cv;

@Repository
public class CvServiceDaoImpl implements CvServiceDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Cv> getCVsByUserId(Long userId) {
		Session entityManager = sessionFactory.getCurrentSession();
		return entityManager.createQuery("SELECT c FROM Cv c WHERE c.user.id = :userId", Cv.class)
				.setParameter("userId", userId).getResultList();
	}

	@Override
	public void saveCv(Cv cv) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(cv);
	}

	@Override
	public void deleteCvById(Long cvId) {
		Session session = sessionFactory.getCurrentSession();
		Cv cv = session.get(Cv.class, cvId);  // Load the CV by ID
		if (cv != null) {
			session.delete(cv);  // Delete the CV if it exists
		}
		
	}

	@Override
	public Cv getCvById(Long cvId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Cv.class, cvId);
	}

}
