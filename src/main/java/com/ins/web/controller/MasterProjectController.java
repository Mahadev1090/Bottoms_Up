package com.ins.web.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.security.date.AuthenticationService;
import com.ins.web.security.date.DateTimeProvider;
import com.ins.web.service.MasterProjectService;
import com.ins.web.vo.MasterProjectListResponse;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/masterProjects")
public class MasterProjectController {

	@Autowired
	private MasterProjectService masterProjectService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private DateTimeProvider dateTimeProvider;

	@GetMapping("/getAllProjects")
	@PreAuthorize("hasAuthority('USER_ROLES')")
	public MasterProjectListResponse getAllMasterProjects() {
		List<MasterProjectVo> projectList = masterProjectService.getAllMasterProjects();
		int projectCount = projectList.size();
		MasterProjectListResponse response = new MasterProjectListResponse();
		response.setCount(projectCount);
		response.setProjects(projectList);
		return response;
	}

	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ADMIN_ROLES')")
	public ResponseEntity<Object> saveProject(@RequestBody @Valid MasterProjectRequest project,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = "Invalid Input Data. Please check the provided fields.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", errorMessage));
		}

		if (project.getStartDate() != null || project.getCreatedBy() != null || project.getCreatedOn() != null
				|| project.getProjectKey() != null) {
			String message = "Fields such as id, startDate, project key, createdBy, and createdOn cannot be modified.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", message));
		}

		try {
			if (project.getId() == null) {
				// Create a new project
				ResponseEntity<Map<String, String>> savedProject = masterProjectService.createProject(project);
				return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
			} else {
				// Update an existing project
				String currentUser = authenticationService.getCurrentUsername();
				String currentDateTime = dateTimeProvider.getCurrentDateTime();

				MasterProjectVo existingProject = masterProjectService.getProjectById(project.getId());

				if (project.getStatus() != null) {
					existingProject.setStatus(project.getStatus());
				}
				if (project.getProjectName() != null) {
					existingProject.setProjectName(project.getProjectName());
				}
				if (project.getAccountType() != null) {
					existingProject.setAccountType(project.getAccountType());
				}
				if (project.getProjectAFENum() != null) {
					existingProject.setProjectAFENum(project.getProjectAFENum());
				}
				if (project.getProjectManager() != null) {
					existingProject.setProjectManager(project.getProjectManager());
				}
				if (project.getProjectDescription() != null) {
					existingProject.setProjectDescription(project.getProjectDescription());
				}
				if (project.getProjectApprovedCapex() != null) {
					existingProject.setProjectApprovedCapex(project.getProjectApprovedCapex());
				}
				if (project.getEndDate() != null) {
					existingProject.setEndDate(project.getEndDate());
				}
				if (project.getProjectApprovedOpex() != null) {
					existingProject.setProjectApprovedOpex(project.getProjectApprovedOpex());
				}
				// Set updated by and updated on
				existingProject.setUpdatedBy(currentUser);
				existingProject.setUpdatedOn(currentDateTime);

				// Perform the update
				masterProjectService.updateProject(existingProject);

				return ResponseEntity.ok(existingProject);
			}
		} catch (Exception e) {
			String message = "An unexpected error occurred.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}
}
