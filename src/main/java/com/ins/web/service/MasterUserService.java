package com.ins.web.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.ins.web.dto.SearchResultDTO;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

import jakarta.validation.Valid;

public interface MasterUserService {
	//addUser

//	MasterUserVo newUser(MasterUserVo user) throws Exception;
	
//	MasterUserVo createUser(MasterUserVo user);
	ResponseEntity<Map<String, String>> createUser(@Valid MasterUserRequest masterUserRequest);

	List<MasterUserVo> getAllMasterUsers();
	
	public MasterUserVo getUserById(long userId);
	
	public void updateUser(MasterUserVo user);
	
    SearchResultDTO searchData(SearchRequest searchRequest);

}
