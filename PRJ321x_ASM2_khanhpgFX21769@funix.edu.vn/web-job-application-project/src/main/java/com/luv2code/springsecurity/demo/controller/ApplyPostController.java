package com.luv2code.springsecurity.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springsecurity.demo.entity.ApplyPost;
import com.luv2code.springsecurity.demo.entity.Cv;
import com.luv2code.springsecurity.demo.entity.Recruitment;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.ApplyPostService;
import com.luv2code.springsecurity.demo.service.CvService;
import com.luv2code.springsecurity.demo.service.RecruitmentService;
import com.luv2code.springsecurity.demo.service.UserService;

@Controller
@RequestMapping("/applyPost")
public class ApplyPostController {

	@Autowired
	private ApplyPostService applyPostService;

	@Autowired
	private RecruitmentService recruitmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private CvService cvService;

	@GetMapping("/applyPostForm")
	@Transactional
	public String showApplyPostForm(@RequestParam("recruitmentId") int recruitmentId,
			@RequestParam("userId") Long userId, Model model) {
		Recruitment recruitment = recruitmentService.getRecruitment(recruitmentId);
		User user = userService.findById(userId);

		// Check if the user is an Applicant
		if (user.getRoles() != null) {
			boolean isApplicant = user.getRoles().stream()
					.anyMatch(role -> "ROLE_APPLICANT".equalsIgnoreCase(role.getName()));

			if (!isApplicant) {
				// Redirect non-applicants to the home page
				return "redirect:/?userId=" + user.getId();
			}
		} else {
			return "redirect:/error?message=User roles not found";
		}

		// Check if the user has CVs associated
		if (user.getCvs() == null || user.getCvs().isEmpty()) {
			// Fetch CVs for the user and bind them
			List<Cv> cvs = cvService.getCVsByUserId(userId);
			user.setCvs(cvs);
		}

		// Add attributes to the model
		model.addAttribute("user", user);
		model.addAttribute("recruitment", recruitment);
		model.addAttribute("applyPost", new ApplyPost()); // Create a new ApplyPost object for binding

		return "applyPostForm"; // Return the JSP form page
	}

	// POST method to handle application submission
	@PostMapping("/submitApplyPost")
	public String submitApplyPost(@ModelAttribute("applyPost") ApplyPost applyPost,
			@RequestParam("recruitmentId") int recruitmentId, @RequestParam("userId") Long userId,
			@RequestParam("cvId") Long cvId, @RequestParam("text") String text,
			@RequestParam("createdAt") String createdAt, @RequestParam("fileNameText") String fileNameText,
			Principal principal) {

		Recruitment recruitment = recruitmentService.getRecruitment(recruitmentId);

		// Retrieve the User entity by its ID
		User user = userService.findById(userId);

		// Retrieve the Cv entity by its ID
		Cv cv = cvService.getCVById(cvId);

		applyPost.setRecruitment(recruitment);
		applyPost.setUser(user);
		applyPost.setCv(cv);
		applyPost.setCreatedAt(createdAt);
		applyPost.setText(text);
		applyPost.setNameCv(fileNameText);

		applyPostService.save(applyPost); // Save the application

		return "redirect:/applyPost/confirmation"; // Redirect to home page
	}

	@GetMapping("/confirmation")
	public String showConfirmationPage(Principal principal, Model model) {
		User currentUser = userService.findByUserName(principal.getName());

		model.addAttribute("user", currentUser);

		return "applyPostConfirmation"; // assuming you have applyPostConfirmation.jsp
	}

	@GetMapping("/listApplicants")
	public String listApplicantsByRecruitmentId(@RequestParam("recruitmentId") int recruitmentId, Principal principal,
			Model model) {
		List<ApplyPost> applyPosts = applyPostService.findByRecruitmentId(recruitmentId);
		User user = userService.findByUserName(principal.getName());
		if (user != null) {
			model.addAttribute("hrUser", user);
		}
		model.addAttribute("applyPosts", applyPosts);
		model.addAttribute("recruitmentId", recruitmentId); // Pass recruitment ID for context
		return "list-applicants"; // Render a JSP to display the applicants
	}
	
	@GetMapping("/applyPostList")
	public String showApplicantApplications(@RequestParam("userId") Long userId, Model model) {
		List<ApplyPost> applyPosts = applyPostService.findApplicationByUserId(userId);
		model.addAttribute("applyPosts", applyPosts);
		model.addAttribute("userId", userId);
		
		return "appliedpostmanagerApplicant";
	}
	
	@GetMapping("/delete-applypost")
	public String deleteApplication(@RequestParam("id") int theId, @RequestParam("userId") Long userId) {
		
		applyPostService.deleteApplication(theId);
		
		return "redirect:/companies?userId="+ userId;
	}
	
	// Show form to edit cvId
    @GetMapping("/edit-applypost")
    public String showEditCvForm(@RequestParam("id") int applyPostId,
                                 @RequestParam("userId") Long userId,
                                 Model model) {
    	System.out.println("Reached edit-applypost method");
        
        ApplyPost applyPost = applyPostService.findById(applyPostId);

        // Add the ApplyPost and userId to the model
        model.addAttribute("applyPost", applyPost);
        model.addAttribute("userId", userId);
        
        List<Cv> cvs = cvService.getCVsByUserId(userId); // Get the list of CVs for the user
        model.addAttribute("cvs", cvs);

        // Return the view for editing cvId
        return "editApplyPostForm";
    }

    // Save the updated cvId
    @PostMapping("/edit-applypost")
    public String saveUpdatedCvId(@RequestParam("id") int applyPostId,
                                  @RequestParam("cvId") Long cvId,
                                  @RequestParam("userId") Long userId) {

        // Fetch and update the ApplyPost
        ApplyPost applyPost = applyPostService.findById(applyPostId);
        Cv newCV = cvService.getCVById(cvId);
        applyPost.setCv(newCV); // Update only the cvId
        applyPostService.save(applyPost); // Save the updated ApplyPost

        // Redirect back to the list or a confirmation page
        return "redirect:/?userId="+userId;
    }
}
