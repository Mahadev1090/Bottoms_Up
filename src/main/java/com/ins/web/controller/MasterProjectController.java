package com.ins.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.service.MasterProjectService;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserVo;

import jakarta.validation.Valid;

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
    public ResponseEntity<Object> createProject(@RequestBody @Valid MasterProjectRequest masterProjectRequest, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
            // Build a custom error message or response
            String errorMessage = "Invalid Input Data. Please check the provided fields.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", errorMessage));
        }

        try {
        	ResponseEntity<Map<String, String>> savedProject = masterProjectService.createProject(masterProjectRequest);
            return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle other exceptions, log the error, and return an appropriate response
            String errorMessage = "An unexpected error occurred.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", errorMessage));
        }
    }
}
