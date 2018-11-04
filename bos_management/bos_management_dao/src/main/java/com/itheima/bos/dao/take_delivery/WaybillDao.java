package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.WayBill;

public interface WaybillDao extends JpaRepository<WayBill, Integer>,JpaSpecificationExecutor<WayBill>{
	
}
