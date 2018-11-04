package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.SubArea;

public interface SubareaService {

	Page<SubArea> findByPage(Specification<SubArea> spec, Pageable pageable);

	void save(SubArea subArea);

	List<SubArea> findAll();

	List<Object[]> doPie();

}
