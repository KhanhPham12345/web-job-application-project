package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.ApplyPost;

@Repository
public class ApplyPostDaoImpl implements ApplyPostDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void saveOrUpdate(ApplyPost applyPost) {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(applyPost);
	}

	@Override
	public List<ApplyPost> findByRecruitmentId(int recruitmentId) {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<ApplyPost> query = currentSession.createQuery("FROM ApplyPost WHERE recruitment.id = :recruitmentId",
				ApplyPost.class);
		query.setParameter("recruitmentId", recruitmentId);
		return query.getResultList();
	}

	@Override
	public List<ApplyPost> findByUserId(Long userId) {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<ApplyPost> query = currentSession.createQuery("FROM ApplyPost WHERE user.id = :userId", ApplyPost.class);
		query.setParameter("userId", userId);

		return query.getResultList();
	}

	@Override
	public void deleteApplyPost(int theId) {

		Session currentSession = sessionFactory.getCurrentSession();
		ApplyPost applyPost = currentSession.find(ApplyPost.class, theId);
	    currentSession.remove(applyPost);
	}

	@Override
	public ApplyPost findById(int applyPostId) {
		Session currentSession = sessionFactory.getCurrentSession();
		ApplyPost applyPost = currentSession.get(ApplyPost.class, applyPostId);
		
		return applyPost;
	}

}
