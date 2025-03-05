package com.luv2code.springsecurity.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.entity.WorkExperience;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.service.WorkexpService;

@Controller
@RequestMapping("/workexp")
public class WorkexpController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkexpService workexpService;
	
	@GetMapping("/showFormForUpdate")
	private String showFormForUpdateWorkexp(@RequestParam("id")int theInt, Model theModel) {
		WorkExperience workexp = workexpService.getworkexp(theInt);
		theModel.addAttribute("workexperience",workexp);
		// Add the userId again after update if needed
        Long userId = workexp.getUser().getId(); // Or retrieve from the session if necessary
        theModel.addAttribute("userId", userId);
		return "work-exp-update";
	}
	
	@PostMapping("/update")
	private String updateWorkexp(@Valid @ModelAttribute("workexperience") WorkExperience workexp,
			BindingResult theBindingResult, Model theModel) {
	
		if (theBindingResult.hasErrors()) {
            return "access-denied";
        }
		
		// Fetch the existing work experience entry to retain its user relationship
	    WorkExperience currentworkexp = workexpService.getworkexp(workexp.getId());
	    
	    // Update only the fields that are meant to be changed
	    currentworkexp.setCompanyName(workexp.getCompanyName());
	    currentworkexp.setDescription(workexp.getDescription());
	    currentworkexp.setPosition(workexp.getPosition());
	    currentworkexp.setStartDate(workexp.getStartDate());
	    currentworkexp.setEndDate(workexp.getEndDate());
	    
		workexpService.updateWorkexp(currentworkexp);
		theModel.addAttribute("successMessage", "work experience updated successfully!");
		// Add the userId again after update if needed
        Long userId = currentworkexp.getUser().getId(); 
        
		return "redirect:/?userId="+userId;		
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAddWorkexp(@RequestParam("userId") Long userId, Model theModel) {
	    // Retrieve the User object by userId
	    User user = userService.findById(userId);
	    if (user == null) {
	        // Handle case where user is not found (redirect or show an error)
	        return "error-page";
	    }
	    
	    // Create a new WorkExperience object and set the associated User
	    WorkExperience newWorkexp = new WorkExperience();
	    newWorkexp.setUser(user);  // Associate user with the new WorkExperience
	    
	    // Add WorkExperience and userId to the model
	    theModel.addAttribute("workexperience", newWorkexp);
	    theModel.addAttribute("userId", userId);

	    return "work-exp-form"; // Direct to form for adding work experience
	}
	
	@PostMapping("/save")
	public String saveWorkexp(@ModelAttribute("workexperience") WorkExperience workexp,
	                          @RequestParam("userId") Long userId) {
	    // Fetch and set the user based on the userId
	    User user = userService.findById(userId);
	    if (user == null) {
	        return "error-page"; // Handle error if user is not found
	    }
	    
	    // Set the user to the work experience
	    workexp.setUser(user);
	    
	    // Save the work experience
	    workexpService.updateWorkexp(workexp);

	    // Redirect to the user details page
	    return "redirect:/?userId=" + userId;
	}
	
	@PostMapping("/delete")
	public String deleteWorkexp(@RequestParam("id") int workexpId, @RequestParam("userId") Long userId) {
	    workexpService.deleteWorkexpById(workexpId);
	    return "redirect:/?userId=" + userId;
	}
}
