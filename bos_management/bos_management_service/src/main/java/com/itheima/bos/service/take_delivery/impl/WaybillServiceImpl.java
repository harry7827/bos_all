package com.itheima.bos.service.take_delivery.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WaybillDao;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;

@Service("waybillService")
@Transactional
public class WaybillServiceImpl implements WaybillService {
	@Autowired
	private WaybillDao waybillDao;
	@Override
	public void save(WayBill model) {
		waybillDao.save(model);
	}

	@Override
	public Page<WayBill> pageQuery(Pageable pageable) {
		return waybillDao.findAll(pageable);
	}

	@Override
	public List<WayBill> findAll() {
		return waybillDao.findAll();
	}

}
