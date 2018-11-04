package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.struts2.ServletActionContext;
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
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.common.CommonAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller("roleAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Result(name="success",location="/pages/system/role.html",type="redirect")
public class RoleAction extends CommonAction<Role> {
	@Autowired
	private RoleService roleService;
	private String menuIds;//菜单ids
	private Integer[] permissionIds;//权限ids
	 
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public void setPermissionIds(Integer[] permissionIds) {
		this.permissionIds = permissionIds;
	}
	@Action(value="roleAction_save")
	public String save() {
		roleService.save(menuIds,permissionIds,getModel());
		return SUCCESS;
	}
	@Action(value="roleAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Role> page = roleService.pageQuery(pageable);
		page2Json(page, new String[]{"users","permissions","menus"});
		return NONE;
	}
	@Action(value="roleAction_findAll")
	public String findAll() throws IOException {
		List<Role> list = roleService.findAll();
		list2Json(list, new String[]{"users","permissions","menus"});
		return NONE;
	}
	
	//查询单个角色信息
	@Action(value = "roleAction_findOne")
	public String findOne() throws IOException {
		Role role = roleService.findOne(getModel().getId());
		if (role!=null) {
			Set<Menu> menuSet = role.getMenus();
			Set<Menu> menus=new HashSet<Menu>();
			for (Menu menu : menuSet) {
				if (menu==null || menu.getPId()!=0) {
					menus.add(menu);
				}
			}
			role.setMenus(menus);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"users","roles","childrenMenus","parentMenu"});
			String jsonStr = JSONObject.fromObject(role,jsonConfig).toString();
			ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
			ServletActionContext.getResponse().getWriter().print(jsonStr);
		}
		return NONE;
	}
}
