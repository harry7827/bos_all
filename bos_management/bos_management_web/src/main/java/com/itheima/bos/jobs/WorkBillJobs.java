package com.itheima.bos.jobs;

import java.util.List;
import javax.annotation.Resource;
import com.itheima.bos.dao.take_delivery.WorkBillDao;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.utils.MailUtils;

public class WorkBillJobs {
	@Resource
	private WorkBillDao workBillDao;
	public void sendMail() {
		List<WorkBill> listWorkBill = workBillDao.findAll();
		String content="<table border='1px'><tr><td>工单id</td><td>工单类型 </td><td>取件状态 </td><td>快递员</td></tr>";
		String subject="统计所有工单数据";
		String to="ls@store.com";
		for (WorkBill workBill : listWorkBill) {
			//工单id  工单类型 取件状态 快递员
			Integer id = workBill.getId();
			workBill.getOrder().getOrderType();
			String pickstate = workBill.getPickstate();
			Courier c = workBill.getCourier();
			String cname="";
			if (c!=null) {
				cname=c.getName();
			}
			content += "<tr><td>" + workBill.getId() + "</td><td>" + workBill.getType() + "</td><td>"
					+ workBill.getPickstate() + "</td><td>" + cname + "</td></tr>";

		}
		try {
			MailUtils.sendMail(subject, content, to);
			System.out.println("邮件发送成功。。。");
		} catch (Exception e) {
			System.out.println("邮件发送失败。。。");
		}
	}
}
