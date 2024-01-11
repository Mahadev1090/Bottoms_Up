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

import com.ins.web.dto.MasterUserWithMasterProjectDTO;
import com.ins.web.service.MasterUserService;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/masterUsers")
public class MasterUserController {

    @Autowired
    private MasterUserService masterUserService;

    @GetMapping
	public List<MasterUserVo> getAllMasterUsers() {
		return masterUserService.getAllMasterUsers();
	}
    
    @PostMapping("/adduser")
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
}