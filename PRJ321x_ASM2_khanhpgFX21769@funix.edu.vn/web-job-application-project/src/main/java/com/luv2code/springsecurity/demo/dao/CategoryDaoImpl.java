package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springsecurity.demo.entity.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Category> findAll() {
		Session currentSession = sessionFactory.getCurrentSession();

		Query<Category> theQuery = currentSession.createQuery("from Category", Category.class);

		List<Category> allCategories = theQuery.getResultList();

		return allCategories;
	}

	@Override
	public Category findById(int id) {
		Session currentSession = sessionFactory.getCurrentSession();

		// Create query to find the category by id
		Query<Category> theQuery = currentSession.createQuery("from Category where id=:categoryId", Category.class);
		theQuery.setParameter("categoryId", id);

		// Get the category (or null if not found)
		Category category = theQuery.uniqueResult(); // Use uniqueResult for a single entity

		return category;
	}

}
