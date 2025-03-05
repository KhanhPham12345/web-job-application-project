package com.luv2code.springsecurity.demo.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Map<String, String> roles;
	
	
	@PostConstruct
	protected void loadRoles() {
		
		// using hashmap, could also read this info from a database
		
		roles = new LinkedHashMap<String, String>();
		
		// key=the role, value=display to user 
		roles.put("ROLE_APPLICANT", "APPLICANT");
		roles.put("ROLE_HR", "HR");	
	}
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		
		// add roles to the model for form display
		theModel.addAttribute("roles", roles);
		
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
	                                      BindingResult theBindingResult, 
	                                      Model theModel) {
		String userEmail = theCrmUser.getEmail();
		logger.info("Processing registration form for: " + userEmail);
		
		if(theBindingResult.hasErrors()) {
			
			// Add roles back to the model for display
            theModel.addAttribute("roles", roles);
			return "registration-form";
		}
		
		User existing = userService.findByEmail(userEmail);
		
		User existingUserWithName = userService.findByUserName(theCrmUser.getUserName());
		
		if(existing != null && existingUserWithName != null) {
			theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User name already exists.");
			
			logger.warning("User's name already exists.");
			
			// Add roles back to the model for display
            theModel.addAttribute("roles", roles);
            return "registration-form";
		}
		
		userService.save(theCrmUser);
        
        logger.info("Successfully created user: " + userEmail);
        
        return "registration-confirmation";	
	}
}
