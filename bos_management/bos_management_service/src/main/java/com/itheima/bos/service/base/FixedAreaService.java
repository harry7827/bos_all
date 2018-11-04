package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.FixedArea;

public interface FixedAreaService {

	Page<FixedArea> findByPage(Specification<FixedArea> spec, Pageable pageable);

	void save(FixedArea model);

	void associationCourierToFixedArea(String id, Integer takeTimeId, Integer courierId);

	FixedArea findById(String id);
	
}
