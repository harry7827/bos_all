package com.itheima.bos.service.base;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

public interface CourierService {

	void save(Courier model);

	List<Courier> findByDeltagIsNull();

	Page<Courier> pageQuery(Specification<Courier> spec, Pageable pageable);

	Courier findById(Integer id);

	void update(Courier model);

	void delete(Integer id);

	void deleteBatch(String ids);

	void restoreBatch(String ids);


}
