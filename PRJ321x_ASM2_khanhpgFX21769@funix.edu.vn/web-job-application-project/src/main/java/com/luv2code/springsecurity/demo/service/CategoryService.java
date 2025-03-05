package com.luv2code.springsecurity.demo.service;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Category;

public interface CategoryService {
	public List<Category> findAll();

	public Category findById(int id);
}
