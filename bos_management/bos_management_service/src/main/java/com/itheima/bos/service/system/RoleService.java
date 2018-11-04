package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Role;

public interface RoleService {

	void save(String menuIds, Integer[] permissionIds, Role role);

	Page<Role> pageQuery(Pageable pageable);

	List<Role> findByUser(Integer id);

	List<Role> findAll();
	/**
	 * 查询单个角色数据
	 * @return
	 */
	Role findOne(Integer id);
}
