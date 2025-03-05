package com.luv2code.springsecurity.demo.service;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Cv;

public interface CvService {
	public List<Cv> getCVsByUserId(Long userId);

	public void saveCv(Cv cv);

	public void deleteCvById(Long cvId);

	public Cv getCVById(Long cvId);
}
