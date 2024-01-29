package com.ins.web.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.security.date.AuthenticationService;
import com.ins.web.security.date.DateTimeProvider;
import com.ins.web.vo.MasterProjectRequest;
import com.ins.web.vo.MasterProjectVo;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

@Service
public class MasterProjectServiceImpl implements MasterProjectService {

	private static final Logger logger = LogManager.getLogger(MasterProjectServiceImpl.class);

	@Autowired
	private MasterProjectDao masterProjectDao;
	
	@Autowired
    private AuthenticationService authenticationService; 

    @Autowired
    private DateTimeProvider dateTimeProvider;

	public MasterProjectServiceImpl(MasterProjectDao masterProjectDao) {
		this.masterProjectDao = masterProjectDao;
	}

	@Transactional
	@Override
	public ResponseEntity<Map<String, String>> createProject(MasterProjectRequest masterProjectRequest) {
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterProjectServiceImpl)-> (createProject)");
		String currentUser = authenticationService.getCurrentUsername();
        String currentDateTime = dateTimeProvider.getCurrentDateTime();
        
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
			newProject.setProjectManager(masterProjectRequest.getProjectManager());
			newProject.setCreatedBy(currentUser);
			newProject.setCreatedOn(currentDateTime);
			newProject.setUpdatedBy(currentUser);
			newProject.setUpdatedOn(currentDateTime);

			// save the department to db
			masterProjectDao.save(newProject);
			
			logger.log(Level.INFO, "From service Impl class -> END -> (MasterProjectServiceImpl)-> (createProject)");
			// Return success response
			return new ResponseEntity<>(Collections.singletonMap("message", "Project created successfully"),
					HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			// Handle validation errors
			logger.log(Level.ERROR, "From service Impl class -> Validation error -> (MasterProjectServiceImpl)-> (createProject)");
			String errorMessage = "Validation error: " + e.getMessage();
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// Handle other unexpected errors
			logger.log(Level.ERROR, "From service Impl class -> An unexpected error occurred. -> (MasterProjectServiceImpl)-> (createProject)");
			String errorMessage = "An unexpected error occurred: " + e.getMessage();
			return new ResponseEntity<>(Collections.singletonMap("error", errorMessage),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void validateMasterProjectRequest(MasterProjectRequest masterProjectRequest) throws ValidationException {
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterProjectServiceImpl)-> (validateMasterProjectRequest)");
		// Validating required fields
		if (StringUtils.isEmpty(masterProjectRequest.getProjectName())
				|| StringUtils.isEmpty(masterProjectRequest.getAccountType())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectManager())
				|| StringUtils.isEmpty(masterProjectRequest.getStatus())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectDescription())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectApprovedCapex())
				|| StringUtils.isEmpty(masterProjectRequest.getProjectApprovedOpex())
				|| StringUtils.isEmpty(masterProjectRequest.getStartDate())) {
			throw new ValidationException(
					"Required fields (projectName, accountType, projectManger, status, project Description, ProjectApprovedCapex, ProjectApprovedOpex, startDate, endDate, projectManager) cannot be empty");
		}
		logger.log(Level.INFO, "From service Impl class -> END -> (MasterProjectServiceImpl)-> (validateMasterProjectRequest)");
	}

	private void validateProjectKeyUniqueness(Long projectKey) throws ValidationException {
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterProjectServiceImpl)-> (validateProjectKeyUniqueness)");

		// Check if a project with the same projectKey already exists
		Optional<MasterProjectVo> existingProject = masterProjectDao.findByProjectKey(projectKey);
		if (existingProject.isPresent()) {
			throw new ValidationException("Project with the same project key already exists");
		}
		logger.log(Level.INFO, "From service Impl class -> END -> (MasterProjectServiceImpl)-> (validateProjectKeyUniqueness)");
	}

	@Override
	public List<MasterProjectVo> getAllMasterProjects() {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterProjectServiceImpl)-> (getAllMasterProjects)");
		return masterProjectDao.findAll();
	}
	

	public MasterProjectVo getProjectById(long projectId) {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterProjectServiceImpl)-> (getProjectById)");
        return masterProjectDao.findById(projectId).orElse(null);
    }
	
	public void updateProject(MasterProjectVo user) {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterProjectServiceImpl)-> (updateProject)");
		masterProjectDao.save(user);
    }
}
