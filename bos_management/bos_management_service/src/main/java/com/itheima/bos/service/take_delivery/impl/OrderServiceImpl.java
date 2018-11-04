package com.itheima.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bos.dao.base.AreaDao;
import com.itheima.bos.dao.base.FixedAreaDao;
import com.itheima.bos.dao.take_delivery.OrderDao;
import com.itheima.bos.dao.take_delivery.WorkBillDao;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.crm.service.impl.CustomerServiceImpl;

@Transactional
@Service
@WebService
public class OrderServiceImpl implements OrderService{
	private Logger log=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private OrderDao oderDao;
	@Autowired
	private CustomerServiceImpl proxy;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private FixedAreaDao fixedAreaDao;
	@Autowired
	private WorkBillDao workBillDao;//工单dao
	@Override
	public void add(@WebParam(name="order") Order order) {
		//区域查询
		Area sendArea = order.getSendArea();
		String province1 = sendArea.getProvince();
		String city1 = sendArea.getCity();
		String district1 = sendArea.getDistrict();
		sendArea = areaDao.findByProvinceAndCityAndDistrict(province1, city1, district1);
		Area recArea = order.getRecArea();
		String province2 = recArea.getProvince();
		String city2= recArea.getCity();
		String district2 = recArea.getDistrict();
		recArea = areaDao.findByProvinceAndCityAndDistrict(province2, city2, district2);
		
		String orderType="2";// 分单类型 1 自动分单 2 人工分单
		order.setOrderType(orderType);
		String sendAddress = order.getSendAddress();
		
		if (StringUtils.isNotBlank(sendAddress)) {
			String fixedAreaId = proxy.findFixedAreaIdByAddress(sendAddress);
			if (StringUtils.isNotBlank(fixedAreaId)) {
				FixedArea fixedArea = fixedAreaDao.findOne(fixedAreaId);
				setCourierByFixedArea(fixedArea,order);
			}
		}
		if (order.getOrderType()!="1") {
			if (sendArea!=null) {
				Set<SubArea> subareas = sendArea.getSubareas();
				if (subareas!=null && subareas.size()>0) {
					for (SubArea subArea : subareas) {
						String keyWords = subArea.getKeyWords();
						String assistKeyWords = subArea.getAssistKeyWords();
						if (StringUtils.isNotBlank(sendAddress) && (sendAddress.contains(keyWords) || sendAddress.contains(assistKeyWords)) ) {
							FixedArea fixedArea = subArea.getFixedArea();
							setCourierByFixedArea(fixedArea,order);
						}
					}
				}
			}
		}
		order.setOrderTime(new Date());
		order.setSendArea(sendArea);
		order.setRecArea(recArea);
		order.setStatus("1");// 订单状态 1 待取件 2 运输中 3 已签收 4 异常
		order.setOrderNum(UUID.randomUUID().toString());// 生成订单编号
		
		if (sendArea==null) {
			log.info("发件人区域填写有误");
		}
		if (recArea==null) {
			log.info("收件人区域填写有误");
		}
		
		generateWorkBill(order);
		oderDao.save(order);
	}
	private void setCourierByFixedArea(FixedArea fixedArea,Order order) {
		Set<Courier> couriers = fixedArea.getCouriers();
		if (couriers!=null && couriers.size()>0) {
			for (Courier courier : couriers) {
				if (courier!=null) {
					order.setOrderType("1");
					order.setCourier(courier);
					break;
				}
			}
		}
	}
	private void generateWorkBill(Order order) {
		WorkBill workBill=new WorkBill();
		workBill.setType("1");//1新 2追 3 工单类型
		workBill.setPickstate("待取件");//取件状态
		workBill.setBuildtime(new Date());
		workBill.setAttachbilltimes(1);//追单次数
		workBill.setRemark(order.getRemark());//备注
		workBill.setSmsNumber("12345667");///短信序号
		workBill.setCourier(order.getCourier());
		workBill.setOrder(order);
		workBillDao.save(workBill);
		if (order.getCourier()!=null) {
			log.info("发送短信给快递员成功：将用户地址和手机号码发送给快递员"+";客户手机号码:"+order.getTelephone()+"发件人手机号:"+order.getSendMobile()+";用户地址:"+order.getSendAddress());
		}else{
			log.info("订单手动分发");
		}
	}
	
}
