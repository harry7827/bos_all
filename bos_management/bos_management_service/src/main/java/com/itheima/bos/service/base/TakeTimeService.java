package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.TakeTime;

public interface TakeTimeService {

	List<TakeTime> findAll();

	Page<TakeTime> pageQuery(Pageable pageable);

	void deleteBatch(String ids);

	void save(TakeTime model);

}
