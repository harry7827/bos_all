package com.itheima.crm.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import com.itheima.crm.domain.Customer;

public interface CustomerService {
	/**
	 * 查询客户所有数据
	 * @return
	 */
	
	List<Customer> findAll();
	/**
	 * 查询未关联定区的数据
	 * @return
	 */
	
	List<Customer> findByFixedAreaIdIsNull();
	
	/**
	 * 查询已经关联定区的数据
	 * @param fixedAreaId
	 * @return
	 */
	List<Customer> findByFixedAreaId(String fixedAreaId);
	/**
	 * 关联客户到定区
	 * @param fixedAreaId
	 * @return
	 */
	void assignCustomers2FixedArea(String fixedAreaId, List<Integer> customerIds);
	/**
	 * 用户注册
	 * @param fixedAreaId
	 * @return
	 */
	void regist(Customer customer);
	/**
	 * 根据电话查用户信息
	 * @param fixedAreaId
	 * @return
	 */
	Customer findByTelephone(String telephone);
	/**
	 * 用户激活
	 * @param fixedAreaId
	 * @return
	 */
	void activeMail(String telephone);
	/**
	 * 用户登入验证
	 * @param fixedAreaId
	 * @return
	 */
	Customer login(String telephone, String password);
	String findFixedAreaIdByAddress(String address);
}