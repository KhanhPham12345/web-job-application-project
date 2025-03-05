package com.luv2code.springsecurity.demo.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.entity.Recruitment;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.CompanyService;
import com.luv2code.springsecurity.demo.service.RecruitmentService;
import com.luv2code.springsecurity.demo.service.UserService;

@Controller
@RequestMapping("/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RecruitmentService recruitmentService;

	@GetMapping("/linkedRecruitments")
	public String getCompaniesWithLinkedRecruitments(@RequestParam("userId") Long userId, Model model) {
		List<Company> companies = companyService.getCompanyByUserId(userId);
		model.addAttribute("linkedCompanies", companies);
		model.addAttribute("user",userService.findById(userId));
		return "linked-companies";
	}

	@GetMapping("/companyDetails")
	public String showCompanyDetails(@RequestParam("companyId") Long companyId,@RequestParam("userId") Long userId, Model model) {

		Company company = companyService.findById(companyId);
		List<Recruitment> recruitments = recruitmentService.findByCompanyId(companyId);
		User hrUser = userService.findById(userId);
		
	    model.addAttribute("user", hrUser);
		model.addAttribute("company", company);
		model.addAttribute("recruitments", recruitments);

		return "companyDetails";

	}

	@GetMapping("/editCompany")
	public String getEditCompanyForm(@RequestParam("companyId") Long id,@RequestParam("userId") Long userId, Model model) {
		Company currentCompany = companyService.findById(id);
		User hrUser = userService.findById(userId);
	    model.addAttribute("user", hrUser);
		model.addAttribute("company", currentCompany);
		return "editCompany";
	}

	@PostMapping("/updateCompany")
	public String updateCompany(@RequestParam("userId") Long userId,
			@RequestParam("companyId") Long companyId,
			@RequestParam("nameCompany") String nameCompany, @RequestParam("address") String address,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("description") String description, @RequestParam("logo") String logoUrl,
			RedirectAttributes redirectAttributes) {
		try {
			Company company = companyService.findById(companyId);
			company.setNameCompany(nameCompany);
			company.setAddress(address);
			company.setEmail(email);
			company.setPhoneNumber(phoneNumber);
			company.setDescription(description);

			// Convert Google Drive URL to thumbnail format
			String thumbnailUrl = convertToThumbnailUrl(logoUrl);
			company.setLogo(thumbnailUrl);

			companyService.updateCompany(company);
			redirectAttributes.addFlashAttribute("successMessage", "Company updated successfully!");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Error updating company.");
		}
		return "redirect:/companies/companyDetails?companyId=" + companyId + "&userId=" + userId;
	}
	
	@PostMapping("/deleteCompany")
	public String deleteCompany(@RequestParam("companyId")Long companyId, @RequestParam("userId")Long userId) {
		companyService.delete(companyId);
		return "redirect:/?userId=" + userId;
	}
	
	@PostMapping("/saveNewCompany")
	public String saveNewCompany(@Valid @ModelAttribute("company") Company company, 
	                              BindingResult result,@RequestParam("userId") Long userId, 
	                              Model model) {
	    
	    // Check if validation errors exist
	    if (result.hasErrors()) {
	    	System.out.println("Validation Company errors:");
	        result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
	    	
	        // If there are validation errors, return to the form page with the userId
	        model.addAttribute("user", userService.findById(userId));
			
	        return "user-detail-HR"; // Ensure you return to the same page where the form is
	    }
	    
	    // If no validation errors, process and save the company
	    String newLogoLink = convertToThumbnailUrl(company.getLogo());
	    company.setLogo(newLogoLink);
	    company.setStatus(1);  // 1 means active status
	    companyService.saveNewCompany(company);

	    // Redirect to a page (or the form itself) after saving the company
	    return "redirect:/?userId=" + userId; // Or a more specific URL for success
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

}
