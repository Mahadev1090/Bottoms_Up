package com.ins.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ins.web.vo.MasterProjectVo;

public interface MasterProjectDao extends JpaRepository<MasterProjectVo, Long> {

}
