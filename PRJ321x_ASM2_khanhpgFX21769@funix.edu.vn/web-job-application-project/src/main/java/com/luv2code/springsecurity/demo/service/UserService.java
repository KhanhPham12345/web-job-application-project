package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.CrmUser;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	
	public User findByEmail(String email);
	
	public User findById(Long id);

    public User findByUserName(String userName);
    
    public List<User> getAll();
    
    public void save(CrmUser crmUser);

	public void updateUser(User user);

	public Role findRoleByName(String formRole);

	public boolean isNameOrEmailDuplicate(@Valid User user);
}
