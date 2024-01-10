package com.ins.web.service;

import java.util.List;

import com.ins.web.vo.MasterUserVo;
import com.ins.web.vo.SearchRequest;

public interface MasterUserService {
	//addUser

//	MasterUserVo newUser(MasterUserVo user) throws Exception;
	
	List<MasterUserVo> searchData(SearchRequest searchRequest);

	MasterUserVo createUser(MasterUserVo user);
}
