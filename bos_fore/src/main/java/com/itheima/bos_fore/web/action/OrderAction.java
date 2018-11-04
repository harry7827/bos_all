package com.itheima.bos_fore.web.action;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.itheima.bos.service.take_delivery.impl.Area;
import com.itheima.bos.service.take_delivery.impl.Order;
import com.itheima.bos.service.take_delivery.impl.OrderServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("orderAction")
@Namespace("/")
@ParentPackage("struts-default")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {
	private Log log=LogFactory.getLog(OrderAction.class);
	@Resource
	private OrderServiceImpl proxy;
	private String sendAreaInfo;
	private String recAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}


	private Order model=new Order();
	@Override
	public Order getModel() {
		return model;
	}


	@Action(value="orderAction_add",results = {
			@Result(name = "success", location = "/signup-success.html", type = "redirect"),
			@Result(name = "error", location = "/signup-fail.html", type = "redirect") })
	public String save() {
		System.out.println("sendAreaInfo:"+sendAreaInfo+"===recAreaInfo:"+recAreaInfo);
		if (StringUtils.isNotBlank(sendAreaInfo)) {
			String[] split = sendAreaInfo.split("/");
			Area sendArea=new Area();
			sendArea.setProvince(split[0]);
			sendArea.setCity(split[1]);
			sendArea.setDistrict(split[2]);
			model.setSendArea(sendArea);
		}
		if (StringUtils.isNotBlank(recAreaInfo)) {
			String[] split = recAreaInfo.split("/");
			Area recArea=new Area();
			recArea.setProvince(split[0]);
			recArea.setCity(split[1]);
			recArea.setDistrict(split[2]);
			model.setRecArea(recArea);
		}
		proxy.add(model);
		return "success";
	}
	
}
