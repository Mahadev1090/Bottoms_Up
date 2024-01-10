package com.ins.web.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ins.web.vo.MasterUserVo;

public interface MasterUserDao extends JpaRepository<MasterUserVo, Long> {
	List<MasterUserVo> findByName(String name);
	List<MasterUserVo> findByTitle(String title);
	List<MasterUserVo> findByStatus(String status);
	List<MasterUserVo> findByCompany(String company);
	Optional<MasterUserVo> findById(Long id);



}
