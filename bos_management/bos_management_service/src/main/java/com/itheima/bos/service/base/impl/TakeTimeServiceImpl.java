package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.TakeTimeDao;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;

@Service("takeTimeService")
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
	
	@Autowired
	private TakeTimeDao takeTimeDao;
	@Override
	public List<TakeTime> findAll() {
		return takeTimeDao.findAll();
	}
	@Override
	public Page<TakeTime> pageQuery(Pageable pageable) {
		return takeTimeDao.findAll(pageable);
	}
	@Override
	public void deleteBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idList = ids.split(",");
			for (String id : idList) {
				takeTimeDao.delete(Integer.parseInt(id));
			}
		}
	}
	@Override
	public void save(TakeTime model) {
		takeTimeDao.save(model);
	}

}
