package com.ins.web.service;

import java.util.List;

import com.ins.web.dto.MasterUserWithMasterProjectDTO;
import com.ins.web.vo.MasterUserRequest;
import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

public interface MasterUserService {
	//addUser

//	MasterUserVo newUser(MasterUserVo user) throws Exception;
	
	List<MasterUserWithMasterProjectDTO> searchData(SearchRequest searchRequest);

//	MasterUserVo createUser(MasterUserVo user);
	MasterUserVo createUser(MasterUserRequest masterUserRequest);

	List<MasterUserVo> getAllMasterUsers();
}
