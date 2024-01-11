package com.ins.web.service;

import java.util.List;

import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;

public interface MasterProjectService {

	MasterProjectVo createProject(MasterProjectRequest masterProjectRequest);
	
	List<MasterProjectVo> getAllMasterProjects();
}
