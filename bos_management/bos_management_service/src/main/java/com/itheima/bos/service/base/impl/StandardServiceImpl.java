package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardDao;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;

@Service("standardService")
@Transactional
public class StandardServiceImpl implements StandardService{
	@Autowired
	private StandardDao standardDao;
	
	@Override
	public void save(Standard model) {
		standardDao.save(model);
	}
	@Override
	public Standard findById(Integer id) {
		return standardDao.findOne(id);
	}
	@Override
	public void update(Standard model) {
		standardDao.save(model);
	}
	@Override
	public void delete(Integer id) {
		standardDao.delete(id);
	}
	@Override
	public Page<Standard> pageQuery(Pageable pageable) {
		Page<Standard> page = standardDao.findAll(pageable);
		return page;
	}
	@Override
	//@RequiresPermissions("waybill")
	public List<Standard> findAll() {
		return standardDao.findAll();
	}
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsStr = ids.split(",");
			for (String idStr : idsStr) {
				Integer id = Integer.parseInt(idStr);
				standardDao.delete(id);
			}
		}
	}
}
