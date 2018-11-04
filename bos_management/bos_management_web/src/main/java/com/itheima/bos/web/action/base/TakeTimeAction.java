package com.itheima.bos.web.action.base;

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
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("takeTimeAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Result(name="success",location="/pages/base/take_time.html",type="redirect")
public class TakeTimeAction extends CommonAction<TakeTime> {
	@Autowired
	private TakeTimeService takeTimeService;
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="takeTimeAction_findAll")
	public String findAll() throws IOException {
		List<TakeTime> list=takeTimeService.findAll();
		list2Json(list, null);
		return NONE;
	}
	@Action(value="takeTimeAction_save")
	public String save() throws IOException {
		takeTimeService.save(model);
		return "success";
	}
	@Action(value="takeTimeAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Page<TakeTime> page = takeTimeService.pageQuery(pageable);
		page2Json(page, new String [] {"subTakeTimes"});
		return NONE;
	}
	@Action(value="takeTimeAction_deleteBatch")
	public String deleteBatch() throws IOException {
		takeTimeService.deleteBatch(ids);
		return "success";
	}
}
