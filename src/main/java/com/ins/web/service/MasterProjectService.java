package com.ins.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;


public interface MasterProjectService {

//	MasterProjectVo createProject(MasterProjectRequest masterProjectRequest);
	ResponseEntity<Map<String, String>> createProject(MasterProjectRequest masterProjectRequest);

	List<MasterProjectVo> getAllMasterProjects();
	
	public MasterProjectVo getUserById(long projectId);
	
	public void updateProject(MasterProjectVo user);

}
