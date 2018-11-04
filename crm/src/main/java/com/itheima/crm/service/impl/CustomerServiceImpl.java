package com.itheima.crm.service.impl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerDao;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

@WebService
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerDao customerDao;
	@Override
	
	public List<Customer> findAll() {
		return customerDao.findAll();
	}
	@Override
	public List<Customer> findByFixedAreaIdIsNull() {
		return customerDao.findByFixedAreaIdIsNull();
	}
	@Override
	public List<Customer> findByFixedAreaId(String fixedAreaId) {
		return customerDao.findByFixedAreaId(fixedAreaId);
	}
	@Override
	public void assignCustomers2FixedArea(String fixedAreaId,List<Integer> customerIds) {
		customerDao.setFixedAreaIdIsNull(fixedAreaId);
		if (customerIds!=null&&customerIds.size()>0) {
			for (Integer id : customerIds) {
				customerDao.assignCustomers2FixedArea(fixedAreaId,id);
			}
		}
	}
	@Override
	public void regist(Customer customer) {
		customerDao.save(customer);
	}
	@Override
	public Customer findByTelephone(String telephone) {
		return customerDao.findByTelephone(telephone);
	}
	@Override
	public void activeMail(String telephone) {
		customerDao.activeMail(telephone);
	}
	@Override
	public Customer login(String telephone, String password) {
		return customerDao.findByTelephoneAndPassword(telephone,password);
	}
	@Override
	public String findFixedAreaIdByAddress(String address) {
		Customer customer = customerDao.findByAddressLike("%"+address+"%");
		if (customer==null) {
			return null;
		}
		return customer.getFixedAreaId();
	}
}