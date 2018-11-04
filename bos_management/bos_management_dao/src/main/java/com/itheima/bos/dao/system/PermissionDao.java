package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;

public interface PermissionDao extends JpaRepository<Permission, Integer>,JpaSpecificationExecutor<Permission>{
	@Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
	List<Permission> findByUser(Integer id);
}
