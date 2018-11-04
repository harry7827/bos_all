package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.base.CourierDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

@Service("courierService")
@Transactional
public class CourierServiceImpl implements CourierService{
	@Autowired
	private CourierDao courierDao;
	
	@Override
	public void save(Courier model) {
		courierDao.save(model);
	}
	@Override
	public Courier findById(Integer id) {
		return courierDao.findOne(id);
	}
	@Override
	public void update(Courier model) {
		courierDao.save(model);
	}
	@Override
	public void delete(Integer id) {
		courierDao.delete(id);
	}
	@Override
	public Page<Courier> pageQuery(Specification<Courier> spec,Pageable pageable) {
		Page<Courier> page = courierDao.findAll(spec, pageable);
		return page;
	}
	@Override
	public List<Courier> findByDeltagIsNull() {
		return courierDao.findByDeltagIsNull();
	}
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idList = ids.split(",");
			for (String idStr : idList) {
				Integer id = Integer.parseInt(idStr);
				courierDao.deleteCourier(id);
			}
		}
	}
	@Override
	public void restoreBatch(String ids) {
		String[] idList = ids.split(",");
		for (String idStr : idList) {
			Integer id = Integer.parseInt(idStr);
			courierDao.restoreCourier(id);
		}
	}
	
}
