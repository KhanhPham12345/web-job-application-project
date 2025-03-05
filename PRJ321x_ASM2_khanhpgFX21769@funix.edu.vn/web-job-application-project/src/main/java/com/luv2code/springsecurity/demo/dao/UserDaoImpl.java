package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findByUserName(String theUserName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> theQuery = currentSession.createQuery("from User u where u.userName = :uName", User.class);
		theQuery.setParameter("uName", theUserName);
		
		User theUser = null;
		
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			System.out.println("User not found with user name: " + theUserName);
			theUser = null;
		}
		
		return theUser;
	}
	
	@Override
	public User findByEmail(String email) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> theQuery = currentSession.createQuery("from User u where u.email = :email", User.class);
		theQuery.setParameter("email", email);
		
		User theUser = null;
		
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			System.out.println("User not found with email: " + email);
			theUser = null;
		}
		
		return theUser;
	}
	
	@Override
	public void save(User theUser) {	
		
		try {
	        // Use the current Hibernate session (transaction managed by Spring)
	        Session currentSession = sessionFactory.getCurrentSession();
	        currentSession.saveOrUpdate(theUser); // Save or update the user entity
	     
	    } catch (Exception e) {
	        // Log any exception that occurs
	        System.out.println("Error saving user: " + e.getMessage());
	    }
		
	}

	@Override
	public User getUser(Long id) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<User> theQuery = currentSession.createQuery(
			"SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.id = :userId", User.class
		);
		theQuery.setParameter("userId", id);
		
		User theUser = null;
		try {
			theUser = theQuery.getSingleResult();
		} catch (Exception e) {
			System.out.println("User not found with id: " + id);
			theUser = null;
		}
		
		return theUser;
	}

	@Override
	public List<User> getAll() {
		Session currentSession = sessionFactory.getCurrentSession();
		Query<User> theQuery = currentSession.createQuery("from User", User.class);
		
		return theQuery.getResultList();
	}
}
