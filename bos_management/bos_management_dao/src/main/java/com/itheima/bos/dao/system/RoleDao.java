package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;

public interface RoleDao extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>{
	@Query("select r from Role r inner join r.users u where u.id = ?")
	List<Role> findByUser(Integer id);
	
}
