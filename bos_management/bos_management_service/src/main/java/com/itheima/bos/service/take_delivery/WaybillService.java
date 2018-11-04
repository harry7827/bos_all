package com.itheima.bos.service.take_delivery;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_delivery.WayBill;

public interface WaybillService {

	void save(WayBill model);

	Page<WayBill> pageQuery(Pageable pageable);

	List<WayBill> findAll();

}
