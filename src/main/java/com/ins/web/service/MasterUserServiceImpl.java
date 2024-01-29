package com.ins.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.dao.MasterUserDao;
import com.ins.web.dto.MasterProjectDTO;
import com.ins.web.dto.MasterUserWithMasterProjectDTO;
import com.ins.web.dto.SearchResultDTO;
import com.ins.web.security.date.AuthenticationService;
import com.ins.web.security.date.DateTimeProvider;
//import com.ins.web.dao.MasterUserDao;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class MasterUserServiceImpl implements MasterUserService {

	private static final Logger logger = LogManager.getLogger(MasterUserServiceImpl.class);

	@Autowired
	private MasterUserDao masterUserDao;

	@Autowired
	private MasterProjectDao masterProjectDao;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
    private AuthenticationService authenticationService; 

    @Autowired
    private DateTimeProvider dateTimeProvider;

	// Master User Search with project details
	@Override
	public SearchResultDTO searchData(SearchRequest searchRequest) {
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterUserServiceImpl)-> (searchData)");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MasterUserVo> criteriaQuery = criteriaBuilder.createQuery(MasterUserVo.class);
		Root<MasterUserVo> root = criteriaQuery.from(MasterUserVo.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (searchRequest.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), searchRequest.getId()));
		}

		if (!StringUtils.isEmpty(searchRequest.getName())) {
			predicates.add(criteriaBuilder.equal(root.get("name"), searchRequest.getName()));
		}

		if (!StringUtils.isEmpty(searchRequest.getTitle())) {
			predicates.add(criteriaBuilder.equal(root.get("title"), searchRequest.getTitle()));
		}

		if (!StringUtils.isEmpty(searchRequest.getCompany())) {
			predicates.add(criteriaBuilder.equal(root.get("company"), searchRequest.getCompany()));
		}

		if (!StringUtils.isEmpty(searchRequest.getType())) {
			predicates.add(criteriaBuilder.equal(root.get("type"), searchRequest.getType()));
		}

		if (!StringUtils.isEmpty(searchRequest.getLocation())) {
			predicates.add(criteriaBuilder.equal(root.get("location"), searchRequest.getLocation()));
		}

		// MasterProject search
		if (searchRequest.getProjectKey() != null) {
			predicates.add(
					criteriaBuilder.equal(root.get("masterProject").get("projectKey"), searchRequest.getProjectKey()));
		}

		if (!StringUtils.isEmpty(searchRequest.getProjectName())) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("projectName"),
					searchRequest.getProjectName()));
		}

		if (!StringUtils.isEmpty(searchRequest.getProjectAFENum())) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("projectAFENum"),
					searchRequest.getProjectAFENum()));
		}

		if (!StringUtils.isEmpty(searchRequest.getProjectApprovedCapex())) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("projectApprovedCapex"),
					searchRequest.getProjectApprovedCapex()));
		}

		if (!StringUtils.isEmpty(searchRequest.getProjectApprovedOpex())) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("projectApprovedOpex"),
					searchRequest.getProjectApprovedOpex()));
		}

		if (!StringUtils.isEmpty(searchRequest.getAccountType())) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("accountType"),
					searchRequest.getAccountType()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if (!StringUtils.isEmpty(searchRequest.getSortField())) {
			List<Order> orders = new ArrayList<>();
			if ("asc".equalsIgnoreCase(searchRequest.getSortOrder())) {
				orders.add(criteriaBuilder.asc(root.get(searchRequest.getSortField())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(searchRequest.getSortField())));
			}
			criteriaQuery.orderBy(orders);
		}

		TypedQuery<MasterUserVo> query = entityManager.createQuery(criteriaQuery);

		// New logic for startId and endId
		if (searchRequest.getStartIndex() > 0) {
			query.setFirstResult(searchRequest.getStartIndex() - 1);
		}

		if (searchRequest.getEndIndex() > 0) {
			query.setMaxResults(searchRequest.getEndIndex() - searchRequest.getStartIndex() + 1);
		}

		List<MasterUserVo> result = query.getResultList();

		if (result.isEmpty()) {
			throw new NoMatchingDataException("Matching data not found");
		}

		// Convert employees to EmployeeWithDepartmentDTO
		List<MasterUserWithMasterProjectDTO> resultDTO = result.stream().map(masterUser -> {
			MasterUserWithMasterProjectDTO masterUserDTO = new MasterUserWithMasterProjectDTO();
			masterUserDTO.setId(masterUser.getId());
			masterUserDTO.setName(masterUser.getName());
			masterUserDTO.setCompany(masterUser.getCompany());
			masterUserDTO.setStatus(masterUser.getStatus());
			masterUserDTO.setType(masterUser.getType());
			masterUserDTO.setManager(masterUser.getManager());
			masterUserDTO.setRates(masterUser.getRates());
			masterUserDTO.setStartDate(masterUser.getStartDate());
			masterUserDTO.setEndDate(masterUser.getEndDate());
			masterUserDTO.setTitle(masterUser.getTitle());
			masterUserDTO.setLocation(masterUser.getLocation());
			masterUserDTO.setCreatedOn(masterUser.getCreatedOn());
			masterUserDTO.setCreatedBy(masterUser.getCreatedBy());
			masterUserDTO.setUpdatedBy(masterUser.getUpdatedBy());
			masterUserDTO.setUpdatedOn(masterUser.getUpdatedOn());

			// Create and set DepartmentDTO
			MasterProjectDTO masterProjectDTO = new MasterProjectDTO();
			MasterProjectVo masterProject = masterUser.getMasterProject();
			if (masterProject != null) {
				masterProjectDTO.setId(masterProject.getId());
				masterProjectDTO.setStatus(masterProject.getStatus());
				masterProjectDTO.setProjectKey(masterProject.getProjectKey());
				masterProjectDTO.setProjectName(masterProject.getProjectName());
				masterProjectDTO.setProjectDescription(masterProject.getProjectDescription());
				masterProjectDTO.setAccountType(masterProject.getAccountType());
				masterProjectDTO.setProjectAFENum(masterProject.getProjectAFENum());
				masterProjectDTO.setProjectApprovedCapex(masterProject.getProjectApprovedCapex());
				masterProjectDTO.setProjectApprovedOpex(masterProject.getProjectApprovedOpex());
				masterProjectDTO.setStartDate(masterProject.getStartDate());
				masterProjectDTO.setEndDate(masterProject.getEndDate());
				masterProjectDTO.setProjectManager(masterProject.getProjectManager());
				masterProjectDTO.setCreatedBy(masterProject.getCreatedBy());
				masterProjectDTO.setCreatedOn(masterProject.getCreatedOn());
				masterProjectDTO.setUpdatedBy(masterProject.getUpdatedBy());
				masterProjectDTO.setUpdatedOn(masterProject.getUpdatedOn());
			}

			masterUserDTO.setProject(masterProjectDTO);

			return masterUserDTO;
		}).collect(Collectors.toList());

		SearchResultDTO searchResultDTO = new SearchResultDTO(resultDTO.size(), resultDTO);
		logger.log(Level.INFO, "From service Impl class -> END -> (MasterUserServiceImpl)-> (searchData)");

        return searchResultDTO;
	}

	// Add Master User
	@Override
	@Transactional
	public ResponseEntity<Map<String, String>> createUser(MasterUserRequest masterUserRequest){
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterUserServiceImpl)-> (createUser)");

		String currentUser = authenticationService.getCurrentUsername();
        String currentDateTime = dateTimeProvider.getCurrentDateTime();
        
		try {
			// Validate masterUserRequest and other input parameters
			validateMasterUserRequest(masterUserRequest);

			// Check if the specified project exists
			Long projectId = masterUserRequest.getProjectId();
			MasterProjectVo project = masterProjectDao.findById(projectId)
					.orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

			// Creating a new user
			MasterUserVo newUser = new MasterUserVo();
			newUser.setName(masterUserRequest.getName());
			newUser.setCompany(masterUserRequest.getCompany());
			newUser.setStatus(masterUserRequest.getStatus());
			newUser.setType(masterUserRequest.getType());
			newUser.setManager(masterUserRequest.getManager());
			newUser.setRates(masterUserRequest.getRates());
			newUser.setStartDate(masterUserRequest.getStartDate());
			newUser.setEndDate(masterUserRequest.getEndDate());
			newUser.setTitle(masterUserRequest.getTitle());
			newUser.setLocation(masterUserRequest.getLocation());
			newUser.setCreatedOn(currentDateTime);
			newUser.setCreatedBy(currentUser);
			newUser.setUpdatedBy(currentUser);
			newUser.setUpdatedOn(currentDateTime);

			newUser.setMasterProject(project);

			// Saving the user to the database
            masterUserDao.save(newUser);

            // Return success response
    		logger.log(Level.ERROR, "From service Impl class -> END -> (MasterUserServiceImpl)-> (createUser)");
            return new ResponseEntity<>(Collections.singletonMap("message", "User created successfully"), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            String errorMessage = e.getMessage();
            return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle other unexpected errors
    		logger.log(Level.ERROR, "From service Impl class -> An unexpected error occurred. -> (MasterUserServiceImpl)-> (createUser)");
            String errorMessage = "An unexpected error occurred: " + e.getMessage();
            return new ResponseEntity<>(Collections.singletonMap("error", errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	private void validateMasterUserRequest(MasterUserRequest masterUserRequest) throws IllegalArgumentException {
		logger.log(Level.INFO, "From service Impl class -> START -> (MasterUserServiceImpl)-> (validateMasterUserRequest)");
		if (masterUserRequest == null) {
			throw new IllegalArgumentException("MasterUserRequest cannot be null");
		}

		// Validating required fields
		if (StringUtils.isEmpty(masterUserRequest.getName()) || StringUtils.isEmpty(masterUserRequest.getCompany())
				|| StringUtils.isEmpty(masterUserRequest.getStatus())
				|| StringUtils.isEmpty(masterUserRequest.getLocation())
				|| StringUtils.isEmpty(masterUserRequest.getTitle())
				|| StringUtils.isEmpty(masterUserRequest.getStartDate())
				|| StringUtils.isEmpty(masterUserRequest.getType())) {
			throw new IllegalArgumentException(
					"Required fields (name, company, status, type, Location, title, startDate) cannot be empty");
		}

//		 Check if the specified project ID is present
        if (masterUserRequest.getProjectId() == null) {
            throw new IllegalArgumentException("Project ID cannot be null");
        }
		logger.log(Level.INFO, "From service Impl class -> END -> (MasterUserServiceImpl)-> (validateMasterUserRequest)");
	}

	
	// Update MasterUser
	@Override
	public List<MasterUserVo> getAllMasterUsers() {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterUserServiceImpl)-> (getAllMasterUsers)");
		return masterUserDao.findAll();
	}
	
	public MasterUserVo getUserById(long userId) {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterUserServiceImpl)-> (getUserById)");
        return masterUserDao.findById(userId).orElse(null);
    }

    public void updateUser(MasterUserVo user) {
		logger.log(Level.INFO, "From service Impl class -> START-END -> (MasterUserServiceImpl)-> (updateUser)");
    	masterUserDao.save(user);
    }
}