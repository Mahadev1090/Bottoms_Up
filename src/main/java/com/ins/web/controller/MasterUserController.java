package com.ins.web.controller;

import java.util.Collections;
import java.util.HashMap;
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

import com.ins.web.dto.SearchResultDTO;
import com.ins.web.security.date.AuthenticationService;
import com.ins.web.security.date.DateTimeProvider;
import com.ins.web.service.MasterUserService;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserListResponse;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/masterUsers")
public class MasterUserController {

	@Autowired
	private MasterUserService masterUserService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private DateTimeProvider dateTimeProvider;

	@GetMapping("/getAllUsers")
	@PreAuthorize("hasAuthority('USER_ROLES')")
	public MasterUserListResponse getAllMasterUsers() {
		List<MasterUserVo> userList = masterUserService.getAllMasterUsers();
		int userCount = userList.size();

		MasterUserListResponse response = new MasterUserListResponse();
		response.setCount(userCount);
		response.setUsers(userList);

		return response;
	}

	@PostMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN_ROLES')")
	public ResponseEntity<Object> searchData(@RequestBody SearchRequest searchRequest) {

		try {

			SearchResultDTO resultDTO = masterUserService.searchData(searchRequest);

			if (resultDTO.getResult().isEmpty()) {
				String message = "No matching data found for the provided criteria.";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			}

			// Include count in the response
			Map<String, Object> response = new HashMap<>();
			response.put("count", resultDTO.getCount());
			response.put("result", resultDTO.getResult());

			return ResponseEntity.ok(response);

		} catch (NoMatchingDataException e) {
			String message = "No matching data found for the provided criteria.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
		} catch (Exception e) {

			String message = "An unexpected error occurred." + e;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}

	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ADMIN_ROLES')")
	public ResponseEntity<Object> saveUser(@RequestBody @Valid MasterUserRequest user, BindingResult bindingResult) {
		System.out.println("entering the saveUser");
		if (bindingResult.hasErrors()) {
			String errorMessage = "Invalid Input Data. Please check the provided fields.";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", errorMessage));
		}

		MasterUserVo existingUser = null;
		try {
			if (user.getId() != null) {
				existingUser = masterUserService.getUserById(user.getId());
			}
			if (existingUser == null) {
				// If the user doesn't exist, it's a new user, so create it
				ResponseEntity<Map<String, String>> savedUser = masterUserService.createUser(user);
				return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
			} else {
				// If the user exists, it's an update
				String currentUser = authenticationService.getCurrentUsername();
				String currentDateTime = dateTimeProvider.getCurrentDateTime();

				// Check if fields that should not be modified are present in the request
				if (user.getStartDate() != null || user.getCreatedBy() != null || user.getCreatedOn() != null) {
					String message = "Fields such as id, startDate, createdBy, and createdOn cannot be modified.";
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body(Collections.singletonMap("message", message));
				}

				// Updating the existing user
				if (user.getName() != null) {
					existingUser.setName(user.getName());
				}
				if (user.getCompany() != null) {
					existingUser.setCompany(user.getCompany());
				}
				if (user.getStatus() != null) {
					existingUser.setStatus(user.getStatus());
				}
				if (user.getType() != null) {
					existingUser.setType(user.getType());
				}
				if (user.getManager() != null) {
					existingUser.setManager(user.getManager());
				}
				if (user.getRates() != 0.0) {
					existingUser.setRates(user.getRates());
				}
				if (user.getEndDate() != null) {
					existingUser.setEndDate(user.getEndDate());
				}
				if (user.getTitle() != null) {
					existingUser.setTitle(user.getTitle());
				}
				if (user.getLocation() != null) {
					existingUser.setLocation(user.getLocation());
				}

				// Set the update information
				existingUser.setUpdatedBy(currentUser);
				existingUser.setUpdatedOn(currentDateTime);

				if (user.getProjectId() != null) {
					MasterProjectVo project = new MasterProjectVo();
					project.setId(user.getProjectId());
					existingUser.setMasterProject(project);
				}

				masterUserService.updateUser(existingUser);

				return ResponseEntity.ok(existingUser);
			}
		} catch (Exception e) {
			String message = "An unexpected error occurred.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}

}