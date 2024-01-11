package com.ins.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserVo;

import jakarta.transaction.Transactional;

@Service
public class MasterProjectServiceImpl implements MasterProjectService {
	
	@Autowired
	private MasterProjectDao masterProjectDao;
	
	public MasterProjectServiceImpl(MasterProjectDao masterProjectDao) {
        this.masterProjectDao = masterProjectDao;
    }
	
	@Transactional
	@Override
	public MasterProjectVo createProject(MasterProjectRequest masterProjectRequest) {
		
		 //create a new Department
    	MasterProjectVo newProject = new MasterProjectVo();
    	newProject.setStatus(masterProjectRequest.getStatus());
    	newProject.setProjectKey(masterProjectRequest.getProjectKey());
    	newProject.setProjectName(masterProjectRequest.getProjectName());
    	newProject.setProjectDescription(masterProjectRequest.getProjectDescription());
    	newProject.setAccountType(masterProjectRequest.getAccountType());
    	newProject.setProjectAFENum(masterProjectRequest.getProjectAFENum());
    	newProject.setProjectApprovedCapex(masterProjectRequest.getProjectApprovedCapex());
    	newProject.setProjectApprovedOpex(masterProjectRequest.getProjectApprovedOpex());
    	newProject.setStartDate(masterProjectRequest.getStartDate());
    	newProject.setEndDate(masterProjectRequest.getEndDate());
    	newProject.setProjectManger(masterProjectRequest.getProjectManger());
    	newProject.setCreatedBy(masterProjectRequest.getCreatedBy());
    	newProject.setCreatedOn(masterProjectRequest.getCreatedOn());
    	newProject.setUpdatedBy(masterProjectRequest.getUpdatedBy());
    	newProject.setUpdatedOn(masterProjectRequest.getUpdatedOn());
    	
    	//save the department to db
    	return masterProjectDao.save(newProject);
	}
	
	
	@Override
	public List<MasterProjectVo> getAllMasterProjects() {
		return masterProjectDao.findAll();
	}
}
