package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

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

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("waybillAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Result(name="success",location="/pages/base/waybill_quick.html",type="redirect")
public class WaybillAction extends CommonAction<WayBill> {
	@Autowired
	private WaybillService waybillService;

	@Action(value="waybillAction_save")
	public String save() throws IOException {
		String rs = "1";
		try {
			waybillService.save(model);
		} catch (Exception e) {
			rs = "0";
		}
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(rs);
		return NONE;
	}
	@Action(value="waybillAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<WayBill> page = waybillService.pageQuery(pageable);
		page2Json(page, null);
		return NONE;
	}
	@Action(value="waybillAction_findAll")
	public String findAll() throws IOException {
		List<WayBill> list = waybillService.findAll();
		list2Json(list, null);
		return NONE;
	}
}