package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.CategoryDao;
import com.luv2code.springsecurity.demo.entity.Category;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	@Transactional
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	@Override
	@Transactional
	public Category findById(int id) {
		return categoryDao.findById(id);
	}

}
