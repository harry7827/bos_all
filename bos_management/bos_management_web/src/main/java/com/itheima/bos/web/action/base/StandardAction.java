package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.codehaus.jettison.json.JSONString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.common.CommonAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("standardAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Result(name="success",location="/pages/base/standard.html",type="redirect")
public class StandardAction extends CommonAction<Standard> {
	@Autowired
	private StandardService standardService;

	@Action(value="standardAction_save")
	public String save() {
		standardService.save(model);
		return "success";
	}
	@Action(value="standardAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Standard> page = standardService.pageQuery(pageable);
		page2Json(page, null);
		return NONE;
	}
	@Action(value="standard_findAll")
	public String findAll() throws IOException {
		List<Standard> list = standardService.findAll();
		list2Json(list, null);
		return NONE;
	}
	/*@Action(value="standard_deleteBatch")
	public String deleteBatch() throws IOException {
		standardService.deleteBatch(ids);
		return "success";
	}*/
}
