package com.luv2code.springsecurity.demo.dao;
import java.util.List;

import com.luv2code.springsecurity.demo.entity.User;

public interface UserDao {
	public User findByUserName(String userName);
	
	public void save(User user);
	
	public User findByEmail(String email);
	
	public User getUser(Long id);

	public List<User> getAll();
}
