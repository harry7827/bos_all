package com.itheima.bos.service.base.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.base.SubareaDao;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;

@Service("subareaService")
@Transactional
public class SubareaServiceImpl implements SubareaService {
	@Autowired
	private SubareaDao subareaDao;
	@Override
	public Page<SubArea> findByPage(Specification<SubArea> spec, Pageable pageable) {
		return subareaDao.findAll(spec, pageable);
	}
	
	@Override
	public void save(SubArea subArea) {
		subArea.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		subareaDao.save(subArea);
	}

	@Override
	public List<SubArea> findAll() {
		return subareaDao.findAll();
	}
	//分区分布图
	@Override
	public List<Object[]> doPie() {
		
		return subareaDao.doPie();
	}
}
