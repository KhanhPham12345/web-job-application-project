package com.luv2code.springsecurity.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.luv2code.springsecurity.demo.entity.Cv;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.entity.WorkExperience;
import com.luv2code.springsecurity.demo.service.CvService;
import com.luv2code.springsecurity.demo.service.UserService;

@Controller
public class CvController {

	@Autowired
	private UserService userService;

	@Autowired
	private CvService cvService;

	@GetMapping("/edit-cv")
	public String showEditForm(@RequestParam("cvId") Long cvId, @RequestParam("userId") Long userId, Model model) {
		Cv cv = cvService.getCVById(cvId);

		model.addAttribute("cv", cv);
		model.addAttribute("userId", userId);

		return "edit-cv-form";
	}

	@PostMapping("/update-cv")
	public String updateCv(@RequestParam("id") Long id, @RequestParam("userId") Long userId,
			@RequestParam("cvFile") MultipartFile cvFile) {

		try {
			// Retrieve the existing CV by ID
			Cv existingCv = cvService.getCVById(id);
			if (existingCv == null) {
				throw new RuntimeException("CV not found with ID: " + id);
			}

			// Convert the uploaded file to a byte array
			byte[] fileBytes = cvFile.getBytes();

			// Update the CV file and save it
			existingCv.setFileLink(fileBytes);

			cvService.saveCv(existingCv);

			return "redirect:/user/cvList?userId=" + userId; // Redirect after successful update
		} catch (Exception e) {
			// Handle exception
			e.printStackTrace();
			return "error-page"; // Redirect to an error page in case of failure
		}
	}

	@GetMapping("/delete-cv")
	public String deleteCv(@RequestParam("cvId") Long cvId, @RequestParam("userId") Long userId) {
		cvService.deleteCvById(cvId);
		return "redirect:/user/cvList?userId=" + userId;
	}

	@GetMapping("/create-newcv")
	public String showCreateForm(@RequestParam("userId") Long userId, Model model) {
		User user = userService.findById(userId);
		List<WorkExperience> workExp = user.getWorkExperiences();
		
		model.addAttribute("user", user);
		model.addAttribute("workExperiences",workExp);
		
		return "create-cv-form"; // JSP page where the form is located
	}
	
	
	@PostMapping("/save-newcv")
	// Process the form submission for creating a new CV
	public String saveNewCv(@ModelAttribute("user") User user,
			@ModelAttribute("newWorkExperience") WorkExperience newWorkExperience) {
		// Add the new work experience to the user’s work experience list
		if (newWorkExperience != null && newWorkExperience.getCompanyName() != null) {
			user.getWorkExperiences().add(newWorkExperience);
		}

		// Save the user and associated work experiences
		userService.updateUser(user);
		return "redirect:/user/cvList?userId=" + user.getId();
	}

	@GetMapping("/download-cv")
	public ResponseEntity<byte[]> downloadCv(@RequestParam("cvId") Long cvId) {
		Cv cv = cvService.getCVById(cvId);

		if (cv != null && cv.getFileLink() != null) {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"cv_" + cvId + ".pdf\"")
					.body(cv.getFileLink());
		}
		return ResponseEntity.notFound().build();
	}

}
