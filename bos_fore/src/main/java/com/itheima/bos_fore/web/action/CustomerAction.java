package com.itheima.bos_fore.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.bos_fore.utils.QueueUtils;
import com.itheima.crm.service.impl.Customer;
import com.itheima.crm.service.impl.CustomerServiceImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller("customerAction")
@Namespace("/")
@ParentPackage("struts-default")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	private Log log=LogFactory.getLog(CustomerAction.class);
	@Resource
	private CustomerServiceImpl proxy;
	@Resource
	private RedisTemplate<String,String> redisTemplate;
/*	@Resource
	private QueueUtils queueUtils;*/
	private Customer model=new Customer();
	@Override
	public Customer getModel() {
		return model;
	}
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	private String activeCode;
	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}
	@Action(value="customerAction_sendMsg")
	public String sendMsg() {
		String telephone = model.getTelephone();
		log.info("telephone:"+telephone+"正在注册！");
		
		final String randomNumeric = RandomStringUtils.randomNumeric(4);
		log.info("生成的验证码为："+randomNumeric);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean flag = false;
		if (StringUtils.isNotBlank(telephone)) {
			request.getSession().setAttribute(telephone,randomNumeric);
			/*jmsQueueTemplate.send("sms_message", new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					MapMessage mapMessage = session.createMapMessage();
					mapMessage.setString("telephone", model.getTelephone());
					mapMessage.setString("randomNumeric", randomNumeric);
					return mapMessage;
				}
			});*/
			QueueUtils.sendMsg(model.getTelephone(), randomNumeric);
			flag = true;
		}
		
		try {
			response.getWriter().print(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	@Action(value="customerAction_regist",results = {
			@Result(name = "success", location = "/signup-success.html", type = "redirect"),
			@Result(name = "error", location = "/signup-fail.html", type = "redirect") })
	public String regist() {
		String randomNumeric = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
		if (StringUtils.isNotBlank(randomNumeric) && StringUtils.isNotBlank(checkcode) && checkcode.equals(randomNumeric)) {
			try {
				proxy.regist(model);
				//String content ="";
				String activeCode = RandomStringUtils.randomNumeric(32);
				ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
				opsForValue.set(model.getTelephone(), activeCode, 5, TimeUnit.MINUTES);
				final String content ="欢迎注册速运快递，请点击下面连接激活账号，24小时内有效！<a href=http://localhost/bos_fore/customerAction_activeMail?telephone="+model.getTelephone()+"&activeCode="+activeCode+">激活</a>";
				/*jmsQueueTemplate.send("email_message",new MessageCreator() {
					@Override
					public Message createMessage(Session session) throws JMSException {
						MapMessage message = session.createMapMessage();
						message.setString("content", content);
						message.setString("email", model.getEmail());
						log.info("生产了消息("+"content:"+content+";email:"+model.getEmail()+")");
						return message;
					}
				});*/
				QueueUtils.sendMail(model.getEmail(), content);
				
			} catch (Exception e) {
				return "error";
			}
			return "success";
		}
		return "error";
	}
	@Action(value="customerAction_activeMail",results={
			@Result(name="success",location="/active-success.html",type="redirect"),
			@Result(name="fail",location="/active-fail.html",type="redirect"),
			@Result(name="already",location="/active-already.html",type="redirect")
			})
	public String activeMail() {
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		String telephone = model.getTelephone();
		//手机号码为空，激活码为空时
		if(StringUtils.isBlank(telephone) || StringUtils.isBlank(activeCode)){
			return "fail";
		}
		//redis中激活码为空，用户的激活码和redis中激活码不一致时
		if (StringUtils.isBlank(opsForValue.get(telephone)) || !activeCode.equals(opsForValue.get(telephone))) {
			return "fail";
		}
		//手机号码未注册
		Customer customer = proxy.findByTelephone(telephone);
		if (customer==null) {
			return "fail";
		}
		//手机号码已注册
		if(customer.getType()!=null && customer.getType() == 1 ){
			return "already";
		}
		proxy.activeMail(telephone);
		return "success";
		
	}
	@Action(value="customerAction_login",results={
			@Result(name="success",location="/myhome.html",type="redirect"),
			@Result(name="fail",location="/login.html",type="redirect")
	})
	public String login(){
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		if(checkcode!=null&&checkcode.equals(validateCode) && StringUtils.isNotBlank(model.getTelephone()) 
				&& StringUtils.isNotBlank(model.getPassword())){
			Customer customer = proxy.login(model.getTelephone(), model.getPassword());
			if (customer!=null) {
				return "success";
			}
		}
		return "fail";
	}
	
}
