package com.luv2code.springsecurity.demo.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springsecurity.demo.entity.DateUtil;
import com.luv2code.springsecurity.demo.entity.Recruitment;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.CompanyService;
import com.luv2code.springsecurity.demo.service.RecruitmentService;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.CompanyRecruitmentDTO;

@Controller
public class DemoController {
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private RecruitmentService recruitmentService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String showPopularCompanies(@RequestParam(value = "userId", required = false) Long userId,Model model) {
	    List<CompanyRecruitmentDTO> popularCompanies = companyService.getPopularCompanies();
	    model.addAttribute("popularCompanies", popularCompanies);
	    
	    // Get popular recruitments
        List<Recruitment> popularRecruitments = recruitmentService.getPopularRecruitments();
        
        // Convert the date correctly
        for (Recruitment recruitment : popularRecruitments) {
            if (recruitment.getDeadline() != null) {
                String formattedDate = DateUtil.convertDate(recruitment.getDeadline());
                recruitment.setDeadline(formattedDate);
            }
        }
        
        
        model.addAttribute("popularRecruitments", popularRecruitments);
        
        // Get popular categories
        List<Object[]> popularCategories = recruitmentService.getCategoriesBasedonRecruitment();
        model.addAttribute("popularCategories", popularCategories);
        
        // Get all users
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        
        if (userId != null) {
            model.addAttribute("userId", userId);
        }
        
	    return "home"; // Your JSP page name
	}
	
	@GetMapping("/companies")

    public String showCompanyPostsByHR(@RequestParam(value = "userId", required = false) Long userId, Model model) {

		if (userId == null) {
	        model.addAttribute("errorMessage", "No user ID provided.");
	        return "error"; // An error page or some fallback page
	    }

        // Find the user (HR) by userId
        User hrUser = userService.findById(userId);

        if (hrUser != null && hrUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_HR"))) {

            List<Recruitment> hrRecruitments = recruitmentService.getRecruitmentBasedsOnUserId(hrUser.getId());
            model.addAttribute("hrRecruitments", hrRecruitments);
            model.addAttribute("hrUser", hrUser);
    

        } else {
            model.addAttribute("errorMessage", "No HR found with the given user ID.");
        }

        return "company-posts"; // Your JSP page for displaying the company's posts
	}
	
	@GetMapping("/searchRecruitmentsForHR")
	public String searchRecruitment(@RequestParam("theSearchName")String theSearchName,Principal principal, Model model, HttpSession session) {
				
		Long userId = userService.findByUserName(principal.getName()).getId();
		User hrUser = userService.findById(userId);
		
		if(hrUser != null && hrUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_HR"))) {
			List<Recruitment> searchResults = recruitmentService.searchRecruitmentsHrid(theSearchName, userId);
			
			model.addAttribute("hrRecruitments", searchResults);
			model.addAttribute("hrUser",hrUser);
		}else {
			model.addAttribute("errorMessage", "No HR found with the given user ID.");
		}
		
		return "company-posts";
		
	}
	
	@GetMapping("/showAllRecruitmentsForHR")
    public String showAllRecruitments(@RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "entriesPerPage", defaultValue = "5") String entriesPerPage,
        Model model, Principal principal) {
		
		Long userId = userService.findByUserName(principal.getName()).getId();
		User hrUser = userService.findById(userId);
		model.addAttribute("hrUser", hrUser);
		
        int pageSize = 0;
        if (entriesPerPage.equals("all")) {
            List<Recruitment> recruitments = recruitmentService.searchRecruitmentsHrid("", userId);
            
            //convert date
            formatDate(recruitments);
            
            model.addAttribute("hrRecruitments",recruitments);
        } else {
            pageSize = Integer.parseInt(entriesPerPage);
            List<Recruitment> recruitments = recruitmentService.getRecruitmentsHrid(pageSize, userId);
            
            //convert date
            formatDate(recruitments);
            
            model.addAttribute("hrRecruitments", recruitments);
            model.addAttribute("currentPage", page);
            model.addAttribute("entriesPerPage", entriesPerPage);
        }

        return "company-posts"; // Name of the JSP page
    }
	
	//add request mapping for /systems
	@GetMapping("/systems")
	public String showSystems() {
		return "systems";
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
