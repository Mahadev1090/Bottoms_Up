package com.ins.web.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ins.web.vo.MasterUserVo;

public interface MasterUserDao extends JpaRepository<MasterUserVo, Long> {
	List<MasterUserVo> findByName(String name);
	List<MasterUserVo> findByTitle(String title);
	List<MasterUserVo> findByType(String type);
	List<MasterUserVo> findByStatus(String status);
	List<MasterUserVo> findByCompany(String company);
	List<MasterUserVo> findByLocation(String location);
	Optional<MasterUserVo> findById(Long id);
	
	List<MasterUserVo> findByMasterProjectProjectName(String projectName);
	List<MasterUserVo> findByMasterProjectProjectApprovedCapex(String projectApprovedCapex);
	List<MasterUserVo> findByMasterProjectProjectApprovedOpex(String projectApprovedOpex);
	List<MasterUserVo> findByMasterProjectAccountType(String accountType);
	List<MasterUserVo> findByMasterProjectProjectAFENum(String projectAFENum);
	Optional<MasterUserVo> findByMasterProjectProjectKey(Long projectKey);


	

}
