package com.itheima.bos.service.system.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.system.RoleDao;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	@Override
	public void save(String menuIds, Integer[] permissionIds, Role role) {
		Role pstRole = roleDao.findOne(role.getId());
		if (pstRole!=null) {
			//持久态对象 保存修改后的 并删除所有菜单和权限
			pstRole.setMenus(null);
			pstRole.setPermissions(null);
			pstRole.setKeyword(role.getKeyword());
			pstRole.setName(role.getName());
			pstRole.setDescription(role.getDescription());
		}else{
			pstRole=role;
		}
		
		//保存角色菜单中间表
		Set<Menu> menus = new HashSet<Menu>(); //得到中间表
		if(StringUtils.isNotBlank(menuIds)){
			String[] menuIds2 = menuIds.split(",");
			for (String menuId : menuIds2) {
				Menu menu = new Menu();
				menu.setId(Integer.parseInt(menuId));
				menus.add(menu);//set:更新某一个字段值 add往表中插入数据
			}
			pstRole.setMenus(menus);
		}
		
		//保存角色权限中间表
		Set<Permission> permissions = new HashSet<Permission>();//得到中间表
		if(permissionIds != null && permissionIds.length > 0){
			for (Integer permissionId : permissionIds) {
				Permission p = new Permission();
				p.setId(permissionId);
				permissions.add(p);//往 角色权限中间表插入数据
			}
			pstRole.setPermissions(permissions);
		}
		
		//先保存角色数据
		roleDao.save(pstRole);
	}
	@Override
	public Page<Role> pageQuery(Pageable pageable) {
		return roleDao.findAll(pageable);
	}
	@Override
	public List<Role> findByUser(Integer id) {
		return roleDao.findByUser(id);
	}
	@Override
	public List<Role> findAll() {
		return roleDao.findAll();
	}

	@Override
	public Role findOne(Integer id) {
		Role role = roleDao.findOne(id);
		role.getMenus().size();
		role.getPermissions().size();
		return role;
	}
}
