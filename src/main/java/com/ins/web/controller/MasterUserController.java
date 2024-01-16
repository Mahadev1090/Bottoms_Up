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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.dto.MasterUserWithMasterProjectDTO;
import com.ins.web.service.MasterUserService;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterProjectVo;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/masterUsers")
public class MasterUserController {

    @Autowired
    private MasterUserService masterUserService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('USER_ROLES')")
	public List<MasterUserVo> getAllMasterUsers() {
		return masterUserService.getAllMasterUsers();
	}
    
    @PostMapping("/adduser")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<Object> createUser(@RequestBody @Valid MasterUserRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Build a custom error message or response
            String errorMessage = "Invalid Input Data. Please check the provided fields.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", errorMessage));
        }

        try {
        	ResponseEntity<Map<String, String>> savedUser = masterUserService.createUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle other exceptions, log the error, and return an appropriate response
            String errorMessage = "An unexpected error occurred.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", errorMessage));
        }
    }
    
    @PostMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
	public ResponseEntity<Object> searchData ( @RequestBody SearchRequest searchRequest) {

		try {
			
			List<MasterUserWithMasterProjectDTO> result = masterUserService.searchData(searchRequest);

			if (result.isEmpty()) {
				String message = "No matching data found for the provided criteria.";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			}
			return ResponseEntity.ok(result);
			
		} catch (NoMatchingDataException e) {
			String message = "No matching data found for the provided criteria.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
		} catch (Exception e) {

			String message = "An unexpected error occurred." + e;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}
    
    
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN_ROLES')")
    public ResponseEntity<Object> updateUser(@RequestBody MasterUserRequest user) {
    	
    	// Check if fields that should not be modified are present in the request
        if (user.getStartDate() != null || user.getCreatedBy() != null || user.getCreatedOn() != null) {
            String message = "Fields such as id, startDate, createdBy, and createdOn cannot be modified.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", message));
        }
        try {
            // Fetch the existing user from the database using the ID from the request body
            MasterUserVo existingUser = masterUserService.getUserById(user.getId());

            if (existingUser == null) {
                // Handle the case where the user with the given ID doesn't exist
                String message = "User with ID " + user.getId() + " not found.";
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
            }

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
            if (user.getUpdatedBy() != null) {
                existingUser.setUpdatedBy(user.getUpdatedBy());
            }
            if (user.getUpdatedOn() != null) {
            	existingUser.setUpdatedOn(user.getUpdatedOn());
            }
            if (user.getProjectId() != null) {
                MasterProjectVo project = new MasterProjectVo();
                project.setId(user.getProjectId());
                existingUser.setMasterProject(project);
            }
            

            masterUserService.updateUser(existingUser);

            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            // Handle exceptions and return an appropriate response
            String message = "An unexpected error occurred.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", message));
        }
    }
    
    
}