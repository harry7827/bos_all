package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer> {

	List<Menu> findByParentMenuIsNull();
	@Query(value="select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
	List<Menu> findByUserId(Integer id);
	
}
