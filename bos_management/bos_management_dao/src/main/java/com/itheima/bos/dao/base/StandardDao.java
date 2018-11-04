package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Standard;

public interface StandardDao extends JpaRepository<Standard, Integer> {
	
}
