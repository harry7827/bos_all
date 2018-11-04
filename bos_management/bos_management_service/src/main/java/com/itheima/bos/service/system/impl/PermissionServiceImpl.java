package com.itheima.bos.service.system.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.system.PermissionDao;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	private PermissionDao permissionDao;
	@Override
	public void save(Permission model) {
		permissionDao.save(model);
	}
	@Override
	public Page<Permission> pageQuery(Pageable pageable) {
		return permissionDao.findAll(pageable);
	}
	@Override
	public List<Permission> findAll() {
		return permissionDao.findAll();
	}
	
}
