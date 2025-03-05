package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Category;

public interface CategoryDao {
	public List<Category> findAll();

	public Category findById(int id);
}
