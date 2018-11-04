package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>,JpaSpecificationExecutor<Customer> {

	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);
	@Modifying
	@Query("update Customer set fixedAreaId = ? where id= ?")
	void assignCustomers2FixedArea(String fixedAreaId, Integer id);
	@Modifying
	@Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
	void setFixedAreaIdIsNull(String fixedAreaId);

	Customer findByTelephone(String telephone);
	@Modifying
	@Query("update Customer set type = 1 where telephone= ?")
	void activeMail(String telephone);

	Customer findByTelephoneAndPassword(String telephone,String password);

	Customer findByAddressLike(String address);
	
}