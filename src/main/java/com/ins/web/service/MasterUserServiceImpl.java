package com.ins.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ins.web.dao.MasterProjectDao;
import com.ins.web.dao.MasterUserDao;
import com.ins.web.dto.MasterProjectDTO;
import com.ins.web.dto.MasterUserWithMasterProjectDTO;
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

	@Autowired
	private MasterUserDao masterUserDao;

	@Autowired
	private MasterProjectDao masterProjectDao;

	@PersistenceContext
	private EntityManager entityManager;

	// Master User Search with project details
	@Override
	public List<MasterUserWithMasterProjectDTO> searchData(SearchRequest searchRequest) {
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
		
		//MasterProject search
		if (searchRequest.getProjectKey() != null) {
			predicates.add(criteriaBuilder.equal(root.get("masterProject").get("projectKey"), searchRequest.getProjectKey()));
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
				masterProjectDTO.setProjectManger(masterProject.getProjectManger());
				masterProjectDTO.setCreatedBy(masterProject.getCreatedBy());
				masterProjectDTO.setCreatedOn(masterProject.getCreatedOn());
				masterProjectDTO.setUpdatedBy(masterProject.getUpdatedBy());
				masterProjectDTO.setUpdatedOn(masterProject.getUpdatedOn());
			}

			masterUserDTO.setProject(masterProjectDTO);

			return masterUserDTO;
		}).collect(Collectors.toList());

		return resultDTO;
	}

	// Add Master User
	@Override
	@Transactional
	public MasterUserVo createUser(MasterUserRequest masterUserRequest) {

		MasterProjectVo project = masterProjectDao.findById(masterUserRequest.getProjectId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Department not found with ID: " + masterUserRequest.getProjectId()));

		// creating a new employee
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
		newUser.setCreatedOn(masterUserRequest.getCreatedOn());
		newUser.setCreatedBy(masterUserRequest.getCreatedBy());
		newUser.setUpdatedBy(masterUserRequest.getUpdatedBy());
		newUser.setUpdatedOn(masterUserRequest.getUpdatedOn());

		newUser.setMasterProject(project);

		// saving the employee to db
		return masterUserDao.save(newUser);
	}

	
	@Override
	public List<MasterUserVo> getAllMasterUsers() {
		return masterUserDao.findAll();
	}
}