package com.luv2code.springsecurity.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.RoleDao;
import com.luv2code.springsecurity.demo.dao.UserDao;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password!");
		}

		Collection<Role> roles = user.getRoles();
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(roles));
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public User findById(Long id) {
		User user = userDao.getUser(id);
		user.getWorkExperiences().size(); // Triggers lazy loading
		return user;
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();

		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setEmail(crmUser.getEmail());

		// Fetch both roles from the database
		Role applicantRole = roleDao.findRoleByName(crmUser.getRole());

		// default ROLE_USER for all users
		Role userRole = roleDao.findRoleByName("ROLE_USER");

		List<Role> roles = new ArrayList<>();
		if (applicantRole != null) {
			roles.add(applicantRole);
		}
		if (userRole != null) {
			roles.add(userRole);
		}

		user.setRoles(roles);
		user.setStatus(1);

		userDao.save(user);
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	@Transactional
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		
		// Fetch the existing user from the database
		User theUser = findById(user.getId());

		if (theUser == null) {
			throw new EntityNotFoundException("User with ID " + user.getId() + " not found");
		}
		
		// Update specific fields from the incoming user object
		theUser.setFullName(user.getFullName());
		theUser.setUserName(user.getUserName());
		theUser.setEmail(user.getEmail());
		theUser.setPhoneNumber(user.getPhoneNumber());
		theUser.setStatus(user.getStatus());
		theUser.setDescription(user.getDescription());
	    theUser.setAddress(user.getAddress());

		
		// Handle image URL conversion
		String newImageLink = convertToThumbnailUrl(user.getImage());
		theUser.setImage(newImageLink);

		// Preserve roles if not provided in the update request
		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			theUser.setRoles(user.getRoles());
		}

		// Preserve work experiences if not provided
		if (user.getWorkExperiences() != null && !user.getWorkExperiences().isEmpty()) {
			theUser.setWorkExperiences(user.getWorkExperiences());
		}

		// Preserve CVs if provided, otherwise keep the existing CVs
		if (user.getCvs() != null && !user.getCvs().isEmpty()) {
			theUser.setCvs(user.getCvs());
		}

		// Save the updated user
		userDao.save(theUser);
	}

	private String convertToThumbnailUrl(String driveUrl) {
		// Regex to extract the file ID from the URL
		String fileIdPattern = "https://drive\\.google\\.com/file/d/([^/]+)/view\\?usp=sharing";
		Pattern pattern = Pattern.compile(fileIdPattern);
		Matcher matcher = pattern.matcher(driveUrl);

		if (matcher.find()) {
			String fileId = matcher.group(1);
			return "https://drive.google.com/thumbnail?id=" + fileId;
		}
		// Return the original URL if it doesn't match the expected format
		return driveUrl;

	}

	@Override
	@Transactional
	public Role findRoleByName(String formRole) {
		return roleDao.findRoleByName(formRole);
	}

	@Override
	@Transactional
	public boolean isNameOrEmailDuplicate(@Valid User user) {
		User existingUserByName = userDao.findByUserName(user.getUserName());
        User existingUserByEmail = userDao.findByEmail(user.getEmail());

        return (existingUserByName != null && !existingUserByName.getId().equals(user.getId())) ||
               (existingUserByEmail != null && !existingUserByEmail.getId().equals(user.getId()));
	}
	
}
