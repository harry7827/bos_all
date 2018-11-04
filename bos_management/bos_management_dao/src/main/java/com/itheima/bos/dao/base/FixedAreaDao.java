package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.FixedArea;

public interface FixedAreaDao extends JpaRepository<FixedArea, String>,JpaSpecificationExecutor<FixedArea> {

}
