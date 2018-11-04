package com.itheima.bos.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.UserDao;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	@Override
	public Page<User> pageQuery(Pageable pageable) {
		return userDao.findAll(pageable);
	}
	@Override
	public void save(User model) {
		userDao.save(model);
	}
	@Override
	public void delete(String ids) {
		if (ids!=null && ids.length()>0) {
			String[] splits = ids.split(",");
			for (String split : splits) {
				Integer id=Integer.parseInt(split);
				userDao.delete(id);
			}
		}
	}
	
}