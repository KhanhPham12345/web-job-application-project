package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.WorkExperience;

public interface WorkexpDao {

	WorkExperience getWorkexp(int theInt);

	void updateWorkexp(WorkExperience workexp);

	void deleteWorkexpById(int workexpId);

	List<WorkExperience> getWorkExperienceByUserId(Long userId);

}
