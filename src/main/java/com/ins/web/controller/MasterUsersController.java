package com.ins.web.controller;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ins.web.service.MasterUserService;
import com.ins.web.service.exception.NoMatchingDataException;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

@RestController
@RequestMapping("/api/masterUsers")
public class MasterUsersController {

    @Autowired
    private MasterUserService masterUserService;

    @PostMapping("/adduser")
    public ResponseEntity<MasterUserVo> createUser(@RequestBody MasterUserVo user) {
        MasterUserVo savedUser = masterUserService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    
    @PostMapping("/search")
	public ResponseEntity<Object> searchData ( @RequestBody SearchRequest searchRequest) {

		try {
			
			List<MasterUserVo> result = masterUserService.searchData(searchRequest);

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