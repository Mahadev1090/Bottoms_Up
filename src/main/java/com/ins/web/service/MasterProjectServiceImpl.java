package com.ins.web.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class MasterProjectServiceImpl implements MasterProjectService {

	@Autowired
	private MasterProjectDao masterProjectDao;

	public MasterProjectServiceImpl(MasterProjectDao masterProjectDao) {
		this.masterProjectDao = masterProjectDao;
	}

	@Transactional
	@Override
	public ResponseEntity<Map<String, String>> createProject(MasterProjectRequest masterProjectRequest) {
		try {
			// Validating masterProjectRequest and other input parameters
			validateMasterProjectRequest(masterProjectRequest);

			// Check if the specified project key is unique
			validateProjectKeyUniqueness(masterProjectRequest.getProjectKey());

			// create a new Department
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

			// save the department to db
			masterProjectDao.save(newProject);

			// Return success response
			return new ResponseEntity<>(Collections.singletonMap("message", "Project created successfully"),
					HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			// Handle validation errors
			String errorMessage = "Validation error: " + e.getMessage();
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// Handle other unexpected errors
			String errorMessage = "An unexpected error occurred: " + e.getMessage();
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void validateMasterProjectRequest(MasterProjectRequest masterProjectRequest) throws ValidationException {

		// Validating required fields
		if (StringUtils.isEmpty(masterProjectRequest.getProjectName())
				|| StringUtils.isEmpty(masterProjectRequest.getAccountType())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectManger())
				|| StringUtils.isEmpty(masterProjectRequest.getCreatedBy())
				|| StringUtils.isEmpty(masterProjectRequest.getCreatedOn())
				|| StringUtils.isEmpty(masterProjectRequest.getUpdatedBy())
				|| StringUtils.isEmpty(masterProjectRequest.getStatus())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectDescription())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectApprovedCapex())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectApprovedOpex())
				|| StringUtils.isEmpty(masterProjectRequest.getStartDate())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectManger())
				|| StringUtils.isEmpty(masterProjectRequest.getEndDate())
				|| StringUtils.isEmpty(masterProjectRequest.getUpdatedOn())) {
			throw new ValidationException(
					"Required fields (projectName, accountType, projectManger, createdBy, createdOn, updatedBy, updatedOn, status, project Description, ProjectApprovedCapex, ProjectApprovedOpex, startDate, endDate, projectManager) cannot be empty");
		}
	}

	private void validateProjectKeyUniqueness(Long projectKey) throws ValidationException {
		// Check if a project with the same projectKey already exists
		Optional<MasterProjectVo> existingProject = masterProjectDao.findByProjectKey(projectKey);
		if (existingProject.isPresent()) {
			throw new ValidationException("Project with the same project key already exists");
		}
	}

	@Override
	public List<MasterProjectVo> getAllMasterProjects() {
		return masterProjectDao.findAll();
	}
}
