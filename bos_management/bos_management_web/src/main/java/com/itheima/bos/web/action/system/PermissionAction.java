package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("permissionAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Result(name="success",location="/pages/system/permission.html",type="redirect")
public class PermissionAction extends CommonAction<Permission> {
	@Autowired
	private PermissionService permissionService;

	@Action(value="permissionAction_save")
	public String save() throws IOException {
		permissionService.save(model);
		return "success";
	}
	@Action(value="permissionAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Permission> page = permissionService.pageQuery(pageable);
		page2Json(page, new String[]{"roles"});
		return NONE;
	}
	@Action(value="permissionAction_findAll")
	public String findAll() throws IOException {
		List<Permission> list = permissionService.findAll();
		list2Json(list, new String[]{"roles"});
		return NONE;
	}
}
