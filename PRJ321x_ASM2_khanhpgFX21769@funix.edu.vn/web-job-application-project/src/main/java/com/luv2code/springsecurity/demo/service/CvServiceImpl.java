package com.luv2code.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springsecurity.demo.dao.CvServiceDao;
import com.luv2code.springsecurity.demo.entity.Cv;

@Service
public class CvServiceImpl implements CvService {
	
	@Autowired
	private CvServiceDao cvServicedao;

	@Override
	@Transactional
	public List<Cv> getCVsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return cvServicedao.getCVsByUserId(userId);
	}

	@Override
	@Transactional
	public void saveCv(Cv cv) {
		cvServicedao.saveCv(cv);
		
	}

	@Override
	@Transactional
	public void deleteCvById(Long cvId) {
		cvServicedao.deleteCvById(cvId);
		
	}

	@Override
	@Transactional
	public Cv getCVById(Long cvId) {
		return cvServicedao.getCvById(cvId);
	}

}
