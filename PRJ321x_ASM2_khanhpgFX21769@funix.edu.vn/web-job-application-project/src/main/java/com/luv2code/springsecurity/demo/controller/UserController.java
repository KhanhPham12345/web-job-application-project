package com.luv2code.springsecurity.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.luv2code.springsecurity.demo.entity.Company;
import com.luv2code.springsecurity.demo.entity.Cv;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.entity.WorkExperience;
import com.luv2code.springsecurity.demo.service.CvService;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.service.WorkexpService;
import com.itextpdf.text.Image;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CvService cvService;

	@Autowired
	private WorkexpService workexpService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("id") Long id, Model model) {

		User user = userService.findById(id);

		// Check if user is found
		if (user == null) {
			model.addAttribute("errorMessage", "User not found");
			return "access-denied";
		}

		model.addAttribute("user", user);

		return "user-detail"; // The JSP page to display user details
	}

	@RequestMapping("/showFormForUpdateHR")
	public String showFormForUpdateHRdetails(@RequestParam("userId") Long id, Model model) {

		User currentUser = userService.findById(id);

		// Check if user is found
		if (currentUser == null) {
			model.addAttribute("errorMessage", "User not found");
			return "access-denied";
		}

		model.addAttribute("company", new Company());
		model.addAttribute("user", currentUser);

		return "user-detail-HR";

	}

	@PostMapping("/saveUser")
	public String saveUser(@Valid @ModelAttribute("user") User theUser, BindingResult theBindingResult, Model theModel,
			HttpSession session) {

		// Check if user is logged in
		if (theUser == null) {
			return "fancy-login";
		}
		
		// Check for duplicate fullName or email
        if (userService.isNameOrEmailDuplicate(theUser)) {
            theModel.addAttribute("errorMessage", "Username or email already exists.");
            return "user-detail";
        }
		
		// Handle binding result
		if (theBindingResult.hasErrors()) {
			return "user-detail";
		} else {
			// Save the user
			userService.updateUser(theUser);
			theModel.addAttribute("successMessage", "User has been successfully saved!");
			return "redirect:/?userId=" + theUser.getId();
		}
	}

	@PostMapping("/saveUserHR")
	public String saveUserHR(@Valid @ModelAttribute("user") User user,
			BindingResult bindingResult, Model model, HttpSession session) {

		// Check if user is logged in
		if (user == null) {
			return "fancy-login";
		}
		
		// Check for duplicate fullName or email
        if (userService.isNameOrEmailDuplicate(user)) {
            model.addAttribute("errorMessage", "Full name or email already exists.");
            model.addAttribute("company", new Company());
            model.addAttribute("user", user);
            return "user-detail-HR";
        }
        
		// Check for validation errors
		if (bindingResult.hasErrors()) {
			// If there are errors, return to the form page with an error message
			System.out.println("Validation HR user errors:");
	        bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
	        
	        model.addAttribute("company", new Company());
			model.addAttribute("user", user);
			
			return "user-detail-HR"; // Ensure this matches your JSP page name
		} else {
			// Save the updated user details using the UserService
			userService.updateUser(user);

			// Redirect to the main page using the userId
			return "redirect:/?userId=" + user.getId(); // Adjust the redirect URL as needed
		}

	}

	@GetMapping("/cvList")
	public String listUserCVs(@RequestParam("userId") Long userId, Model model) {
		List<Cv> cvs = cvService.getCVsByUserId(userId);
		model.addAttribute("cvs", cvs);
		return "cv-list";
	}

	@PostMapping("/uploadCv")
	public String uploadCv(@RequestParam("cvFile") MultipartFile cvFile, @RequestParam("userId") Long userId) {

		System.out.println("Upload successful! User ID: " + userId);

		if (!cvFile.isEmpty()) {
			try {
				byte[] pdfData = cvFile.getBytes();

				User user = userService.findById(userId);

				Cv cv = new Cv();
				cv.setFileLink(pdfData);
				cv.setUser(user);

				cvService.saveCv(cv);

				return "cv-list";
			} catch (IOException e) {
				e.printStackTrace();
				return "access-denied";
			}
		}

		return "cv-list";

	}

	@PostMapping("/generatePdf")
	public String generatePdf(@RequestParam("id") Long userId) {
		// Fetch user details and work experience
		User user = userService.findById(userId);
		List<WorkExperience> workExperiences = workexpService.getWorkExperienceByUserId(userId);

		// Create PDF document in memory
		Document document = new Document();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			PdfWriter.getInstance(document, byteArrayOutputStream);
			document.open();

			// Add user details to the PDF
			document.add(new Paragraph("User Details:"));
			document.add(new Paragraph("Profile Picture:"));
			if (user.getImage() != null) {
			    Image profilePic;
				try {
					profilePic = Image.getInstance(user.getImage());
					profilePic.scaleToFit(100, 100); // Adjust size
				    document.add(profilePic);
				} catch (MalformedURLException e) {
					System.err.println("Invalid URL for profile picture: " + user.getImage());
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("Error loading profile picture from URL.");
					e.printStackTrace();
				}
			}
			document.add(new Paragraph("Full Name: " + user.getFullName()));
			document.add(new Paragraph("Email: " + user.getEmail()));
			document.add(new Paragraph("Phone: " + user.getPhoneNumber()));
			//i want document to add user profile pic. here is how profile pic is stored in database: https://drive.google.com/thumbnail?id=1IFs4rYbmmMU-57f1t8s1F1syzQw73l6G

			// Add each work experience entry
			document.add(new Paragraph("\nWork Experience:"));
			for (WorkExperience workExp : workExperiences) {
				document.add(new Paragraph("Company: " + workExp.getCompanyName()));
				document.add(new Paragraph("Position: " + workExp.getPosition()));
				document.add(new Paragraph("Duration: " + workExp.getStartDate() + " to " + workExp.getEndDate()));
				document.add(new Paragraph("Description: " + workExp.getDescription()));
				document.add(new Paragraph("\n"));
			}

			document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
			return "redirect:/?userId=" + user.getId() + "&message=Error generating PDF. Please try again.";
		}

		// Save the PDF as a byte array in the database
		Cv cv = new Cv();
		cv.setUser(user);
		cv.setFileLink(byteArrayOutputStream.toByteArray());
		cvService.saveCv(cv);

		return "redirect:/?userId=" + user.getId() + "&message=PDF generated successfully!";
	}
	
}
