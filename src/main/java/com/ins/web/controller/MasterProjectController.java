package com.ins.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.service.MasterProjectService;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserVo;

@RestController
@RequestMapping("/api/masterProjects")
public class MasterProjectController {
	
	@Autowired
	private MasterProjectService masterProjectService;
	
	
	@GetMapping
	public List<MasterProjectVo> getAllMasterProjects() {
		return masterProjectService.getAllMasterProjects();
	}
    
	@PostMapping("/addProject")
    public ResponseEntity<MasterProjectVo> createProject(@RequestBody MasterProjectRequest masterProjectRequest) {
        MasterProjectVo savedProject = masterProjectService.createProject(masterProjectRequest);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
}
