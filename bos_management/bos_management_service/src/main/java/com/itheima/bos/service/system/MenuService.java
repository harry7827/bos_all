package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Menu;

public interface MenuService {

	List<Menu> findByParentMenuIsNull();

	void save(Menu model);

	Page<Menu> pageQuery(Pageable pageable);

	List<Menu> findMenu();
	
}
