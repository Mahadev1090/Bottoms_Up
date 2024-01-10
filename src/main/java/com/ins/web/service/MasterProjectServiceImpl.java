package com.ins.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.vo.MasterProjectVo;

@Service
public class MasterProjectServiceImpl implements MasterProjectService {
	
	@Autowired
	private MasterProjectDao masterProjectDao;
	
	
	@Override
	public MasterProjectVo createProject(MasterProjectVo project) {
		
		MasterProjectVo savedProject = masterProjectDao.save(project);
		return savedProject;
	}
}
