package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.ApplyPostDao;
import com.luv2code.springsecurity.demo.entity.ApplyPost;


@Service
public class ApplyPostServiceImpl implements ApplyPostService {
	
	@Autowired
	private ApplyPostDao applyPostDao;

	@Override
	@Transactional
	public void save(ApplyPost applyPost) {
		applyPostDao.saveOrUpdate(applyPost);
	}
	
	@Override
	@Transactional
	public List<ApplyPost> findByRecruitmentId(int recruitmentId){
		return applyPostDao.findByRecruitmentId(recruitmentId);
	}

	@Override
	@Transactional
	public List<ApplyPost> findApplicationByUserId(Long userId) {
		return applyPostDao.findByUserId(userId);
	}

	@Override
	@Transactional
	public void deleteApplication(int theId) {
		applyPostDao.deleteApplyPost(theId);
		
	}

	@Override
	@Transactional
	public ApplyPost findById(int applyPostId) {
		// TODO Auto-generated method stub
		return applyPostDao.findById(applyPostId);
	}
}
