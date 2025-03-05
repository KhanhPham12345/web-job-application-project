package com.luv2code.springsecurity.demo.dao;

import java.util.List;

import com.luv2code.springsecurity.demo.entity.Cv;

public interface CvServiceDao {
	public List<Cv>getCVsByUserId(Long userId);

	public void saveCv(Cv cv);

	public void deleteCvById(Long cvId);

	public Cv getCvById(Long cvId);
}
