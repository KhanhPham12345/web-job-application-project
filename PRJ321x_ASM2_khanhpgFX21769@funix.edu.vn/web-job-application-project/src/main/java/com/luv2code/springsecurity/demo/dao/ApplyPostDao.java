package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.ApplyPost;

public interface ApplyPostDao {

	public void saveOrUpdate(ApplyPost applyPost);

	public List<ApplyPost> findByRecruitmentId(int recruitmentId);

	public List<ApplyPost> findByUserId(Long userId);

	public void deleteApplyPost(int theId);

	public ApplyPost findById(int applyPostId);


}
