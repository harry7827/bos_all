package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Permission;

public interface PermissionService {

	void save(Permission model);

	Page<Permission> pageQuery(Pageable pageable);

	List<Permission> findAll();

}
