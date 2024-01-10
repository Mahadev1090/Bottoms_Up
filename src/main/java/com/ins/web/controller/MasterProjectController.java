package com.ins.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.service.MasterProjectService;
import com.ins.web.vo.MasterProjectVo;

@RestController
@RequestMapping("/api/masterProjects")
public class MasterProjectController {
	
	@Autowired
	private MasterProjectService masterProjectService;
	
	@PostMapping("/addMasterProject")
    public ResponseEntity<MasterProjectVo> createProject(@RequestBody MasterProjectVo project) {
        MasterProjectVo savedProject = masterProjectService.createProject(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }
}
