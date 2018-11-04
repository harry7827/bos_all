package com.itheima.bos.service.system.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.system.MenuDao;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;

@Service
@Transactional
public class MenuServiceImpl implements MenuService{
	@Autowired
	private MenuDao menuDao;
	@Override
	public List<Menu> findByParentMenuIsNull() {
		return menuDao.findByParentMenuIsNull();
	}
	@Override
	public void save(Menu model) {
		if (model.getParentMenu()!=null && model.getParentMenu().getId()==0) {
			model.setParentMenu(null);
		}
		menuDao.save(model);
	}
	@Override
	public Page<Menu> pageQuery(Pageable pageable) {
		return menuDao.findAll(pageable);
	}
	@Override
	public List<Menu> findMenu() {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		if (user!=null && "admin".equals(user.getUsername())) {
			return menuDao.findAll();
		}else {
			return menuDao.findByUserId(user.getId());
		}
	}
	
}
