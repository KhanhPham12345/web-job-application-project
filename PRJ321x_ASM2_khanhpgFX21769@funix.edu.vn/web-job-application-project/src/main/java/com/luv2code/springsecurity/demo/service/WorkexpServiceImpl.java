package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.WorkexpDao;
import com.luv2code.springsecurity.demo.entity.WorkExperience;

@Service
public class WorkexpServiceImpl implements WorkexpService {

	@Autowired
	private WorkexpDao workexpdao;
	
	@Override
	@Transactional
	public WorkExperience getworkexp(int theInt) {
		return workexpdao.getWorkexp(theInt);
	}

	@Override
	@Transactional
	public void updateWorkexp(WorkExperience workexp) {
		workexpdao.updateWorkexp(workexp);
	}

	@Override
	@Transactional
	public void deleteWorkexpById(int workexpId) {
		workexpdao.deleteWorkexpById(workexpId);
	}

	@Override
	@Transactional
	public List<WorkExperience> getWorkExperienceByUserId(Long userId) {
		return workexpdao.getWorkExperienceByUserId(userId);
	}

}
