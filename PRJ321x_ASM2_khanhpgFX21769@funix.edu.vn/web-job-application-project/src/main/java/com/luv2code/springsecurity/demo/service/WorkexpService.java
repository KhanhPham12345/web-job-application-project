package com.luv2code.springsecurity.demo.service;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.WorkExperience;

public interface WorkexpService {

	WorkExperience getworkexp(int theInt);

	void updateWorkexp(WorkExperience currentworkexp);

	void deleteWorkexpById(int workexpId);

	List<WorkExperience> getWorkExperienceByUserId(Long userId);

}
