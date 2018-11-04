package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.TakeTime;

public interface TakeTimeDao extends JpaRepository<TakeTime, Integer> {

	List<TakeTime> findByStatusIsNull();

}
