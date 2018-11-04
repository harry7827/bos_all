package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.itheima.bos.domain.base.Standard;

public interface StandardService {
	public void save(Standard model);

	Standard findById(Integer id);

	void delete(Integer id);

	void update(Standard model);

	Page<Standard> pageQuery(Pageable pageable);

	public List<Standard> findAll();

	public void deleteBatch(String ids);
}
