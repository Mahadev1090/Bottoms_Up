package com.ins.web.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ins.web.vo.MasterProjectVo;

public interface MasterProjectDao extends JpaRepository<MasterProjectVo, Long> {

	Optional<MasterProjectVo> findByProjectKey(Long projectKey);

}
