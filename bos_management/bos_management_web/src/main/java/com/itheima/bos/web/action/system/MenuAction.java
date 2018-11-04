package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("menuAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Result(name="success",location="/pages/system/menu.html",type="redirect")
public class MenuAction extends CommonAction<Menu> {
	@Autowired
	private MenuService menuService;

	@Action(value="menuAction_listajax")
	public String listajax() throws IOException {
		List<Menu> list = menuService.findByParentMenuIsNull();
		list2Json(list, new String[]{"page","priority","description","roles","childrenMenus","parentMenu"});
		return NONE;
	}
	@Action(value="menuAction_save")
	public String save() throws IOException {
		menuService.save(model);
		return "success";
	}
	@Action(value="menuAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(Integer.parseInt(model.getPage())-1, rows);
		Page<Menu> page = menuService.pageQuery(pageable);
		page2Json(page, new String[]{"roles","childrenMenus","parentMenu"});
		return NONE;
	}
	@Action(value="menuAction_findMenu")
	public String findMenu() throws IOException{
		List<Menu> list = menuService.findMenu();
		list2Json(list, new String[]{"priority","description","roles","childrenMenus","parentMenu","children","text"});
		return NONE;
	}
}
