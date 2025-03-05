package com.luv2code.springsecurity.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springsecurity.demo.entity.Category;
import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.entity.DateUtil;
import com.luv2code.springsecurity.demo.entity.Recruitment;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.CategoryService;
import com.luv2code.springsecurity.demo.service.CompanyService;
import com.luv2code.springsecurity.demo.service.RecruitmentService;
import com.luv2code.springsecurity.demo.service.UserService;

@Controller
@RequestMapping("/recruitment")
public class RecruitmentController {
	
	@Autowired
	RecruitmentService recruitmentService; 
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CompanyService companyService;
	
	
	@PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping("/apply-post")
    public String addNewRecruitmentPost(@RequestParam(value = "userId") Long userId, Model model) {
	
        User currentUser = userService.findById(userId);  
        
        if (currentUser == null) {
            return "fancy-login";
        }
        
        for (Role role : currentUser.getRoles()) {
            if (role.getName().equals("ROLE_HR")) {
                
                Recruitment newRecruitment = new Recruitment();
                newRecruitment.setUser(currentUser);  // Set the current user as the recruitment user
              
                model.addAttribute("recruitment", newRecruitment);  // Pre-populated recruitment object
                model.addAttribute("categories", categoryService.findAll());
                model.addAttribute("companies", companyService.getAllCompanies());
                
                return "apply-post";
            }
        }
  
        return "access-denied";
    }
	
	
	@PostMapping("/save-post")
	public String saveNewRecruitmentPost(@Valid @ModelAttribute("recruitment") Recruitment recruitment, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			System.out.println("Validation errors:");
	        result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
	        
	        model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("companies", companyService.getAllCompanies());
	        return "apply-post";
	    }
	    
	    recruitment.setView(0);
	    
	    Long userId = recruitment.getUser().getId();
		
        recruitmentService.saveRecruitment(recruitment);
        
        //Return to home.jsp after successfully update or create recruitment
        return "redirect:/?userId="+ userId;
    }
	
	@GetMapping("/UpdateRecruitment")
	public String showFormForUpdate(@RequestParam("id") int id, Model theModel) {
		
		Recruitment recruitment = recruitmentService.getRecruitment(id);
	
		theModel.addAttribute("recruitment", recruitment);
		theModel.addAttribute("categories", categoryService.findAll());
        theModel.addAttribute("companies", companyService.getAllCompanies());
		
		return "apply-post";
	}
	
	@GetMapping("/DetailRecruitmentHR")
	public String showFormForView(@RequestParam("id") int id, Model theModel) {
		
		Recruitment recruitment = recruitmentService.getRecruitment(id);
	
		theModel.addAttribute("recruitment", recruitment);
		theModel.addAttribute("categories", categoryService.findAll());
        theModel.addAttribute("companies", companyService.getAllCompanies());
		
		return "recruitment-view-hr";
	}
	
	@GetMapping("/DeleteRecruitment")
	public String deleteRecruitment(@RequestParam("id") int theId, @RequestParam(value = "userId") Long userId) {
		
		recruitmentService.deleteRecruitment(theId);
		
		return "redirect:/companies?userId="+ userId;
	}
	

	@GetMapping("/showAllRecruitments")
    public String showAllRecruitments(@RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "entriesPerPage", defaultValue = "5") String entriesPerPage,
        Model model, Principal principal) {
		
		Long userId = userService.findByUserName(principal.getName()).getId();
		model.addAttribute("userId", userId);
		
        int pageSize = 0;
        if (entriesPerPage.equals("all")) {
            List<Recruitment> recruitments = recruitmentService.findAllRecruitments();
            
            //convert date
            formatDate(recruitments);
            
            model.addAttribute("allRecruitments",recruitments);
        } else {
            pageSize = Integer.parseInt(entriesPerPage);
            List<Recruitment> recruitments = recruitmentService.getRecruitments(pageSize);
            
            //convert date
            formatDate(recruitments);
            
            model.addAttribute("allRecruitments", recruitments);
            model.addAttribute("currentPage", page);
            model.addAttribute("entriesPerPage", entriesPerPage);
        }

        return "allRecruitments"; // Name of the JSP page
    }
	
	@GetMapping("/search")
	public String searchRecruitment(@RequestParam("theSearchName")String theSearchName,Principal principal, Model model, HttpSession session) {
		
		List<Recruitment> searchResults = recruitmentService.searchRecruitments(theSearchName);
		model.addAttribute("allRecruitments", searchResults);
		
		Long userId = userService.findByUserName(principal.getName()).getId();
		model.addAttribute("userId", userId);
		
		return "allRecruitments";
		
	}
	
	private void formatDate(List<Recruitment> recruitments) {
        for (Recruitment recruitment : recruitments) {
            if (recruitment.getDeadline() != null) {
                String formattedDate = DateUtil.convertDate(recruitment.getDeadline());
                recruitment.setDeadline(formattedDate); // Update the formatted deadline
            }
        }
    }

}
