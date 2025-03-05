package com.luv2code.springsecurity.demo.service;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.ApplyPost;

public interface ApplyPostService {

	public void save(ApplyPost applyPost);

	List<ApplyPost> findByRecruitmentId(int recruitmentId);

	public List<ApplyPost> findApplicationByUserId(Long userId);

	public void deleteApplication(int theId);

	public ApplyPost findById(int applyPostId);
	
}
