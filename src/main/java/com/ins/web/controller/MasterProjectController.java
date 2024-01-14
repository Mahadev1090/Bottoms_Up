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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.service.MasterProjectService;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;

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
	public ResponseEntity<Object> createProject(@RequestBody @Valid MasterProjectRequest masterProjectRequest,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			// Build a custom error message or response
			String errorMessage = "Invalid Input Data. Please check the provided fields.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", errorMessage));
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

	@PutMapping("/update")
	public ResponseEntity<Object> updateProject(@RequestBody MasterProjectVo project) {

		if (project.getStartDate() != null || project.getCreatedBy() != null || project.getCreatedOn() != null || project.getProjectKey() != null) {
			String message = "Fields such as id, startDate, createdBy, and createdOn cannot be modified.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", message));
		}
		
		try {
			// Fetch the existing project from the database using the ID from the request
			// body
			MasterProjectVo existingUser = masterProjectService.getUserById(project.getId());

			if (existingUser == null) {
				// Handle the case where the project with the given ID doesn't exist
				String message = "Project with ID " + project.getId() + " not found.";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			}

			if (project.getStatus() != null) {
				existingUser.setStatus(project.getStatus());
			}
			if (project.getProjectName() != null) {
				existingUser.setProjectName(project.getProjectName());
			}
			if (project.getAccountType() != null) {
				existingUser.setAccountType(project.getAccountType());
			}
			if (project.getProjectAFENum() != null) {
				existingUser.setProjectAFENum(project.getProjectAFENum());
			}
			if (project.getProjectManager() != null) {
				existingUser.setProjectManager(project.getProjectManager());
			}
			if (project.getProjectApprovedCapex() != null) {
				existingUser.setProjectApprovedCapex(project.getProjectApprovedCapex());
			}
			if (project.getEndDate() != null) {
				existingUser.setEndDate(project.getEndDate());
			}
			if (project.getProjectApprovedOpex() != null) {
				existingUser.setProjectApprovedOpex(project.getProjectApprovedOpex());
			}
			if (project.getUpdatedBy() != null) {
				existingUser.setUpdatedBy(project.getUpdatedBy());
			}
			if (project.getUpdatedOn() != null) {
				existingUser.setUpdatedOn(project.getUpdatedOn());
			}

			masterProjectService.updateProject(existingUser);

			return ResponseEntity.ok(existingUser);
		} catch (Exception e) {
			// Handle exceptions and return an appropriate response
			String message = "An unexpected error occurred.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}
}
