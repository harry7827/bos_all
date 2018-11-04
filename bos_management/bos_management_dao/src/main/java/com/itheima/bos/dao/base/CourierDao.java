package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier>{
	
	@Modifying
	@Query("update Courier set deltag = '1' where id = ?")
	void deleteCourier(Integer id);
	
	@Modifying
	@Query("update Courier set deltag = null where id = ?")
	void restoreCourier(Integer id);

	List<Courier> findByDeltagIsNull();
	
}
