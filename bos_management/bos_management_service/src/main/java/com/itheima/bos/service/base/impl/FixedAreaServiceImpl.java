package com.itheima.bos.service.base.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierDao;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

@Service("fixedAreaService")
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Autowired
	private CourierDao courierDao;
	@Autowired
	private TakeTimeDao takeTimeDao;
	@Override
	public Page<FixedArea> findByPage(Specification<FixedArea> spec, Pageable pageable) {
		return fixedAreaDao.findAll(spec, pageable);
	}
	@Override
	public void save(FixedArea model) {
		model.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		fixedAreaDao.save(model);
	}
	@Override
	public void associationCourierToFixedArea(String id, Integer takeTimeId, Integer courierId) {
		FixedArea fixedArea = fixedAreaDao.findOne(id);
		Courier courier = courierDao.findOne(courierId);
		fixedArea.getCouriers().add(courier);
		TakeTime takeTime = takeTimeDao.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
	}
	@Override
	public FixedArea findById(String id) {
		return fixedAreaDao.findOne(id);
	}
	
}
