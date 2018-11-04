package com.itheima.bos_fore.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class QueueUtils {
	/*static {
		System.out.println("jaizai");
	}*/
	private static Log log=LogFactory.getLog(QueueUtils.class);
	@Autowired
	private JmsTemplate jmsQueueTemplate;
	public JmsTemplate getJmsQueueTemplate() {
		return jmsQueueTemplate;
	}

	private static QueueUtils queueUtils;
	/*@Autowired
	public void setJT(JmsTemplate jmsQueueTemplate) {
		System.out.println("set");
		QueueUtils.jmsQueueTemplate=jmsQueueTemplate;
	}*/
	/*@Resource(name="jmsQueueTemplate")
	public void setJT(JmsTemplate jmsQueueTemplate) {
		System.out.println("set");
		QueueUtils.jmsQueueTemplate=jmsQueueTemplate;
	}*/
	/*@Resource
	public void setJT(JmsTemplate jmsQueueTemplate) {
		System.out.println("set");
		this.jmsQueueTemplate=jmsQueueTemplate;
	}*/
	
	@PostConstruct
	public void init() {
		queueUtils=this;
		queueUtils.jmsQueueTemplate=this.jmsQueueTemplate;
		//QueueUtils.jmsQueueTemplate=queueUtils.jmsQueueTemplate;
	}
	public static void sendMsg(final String telephone,final String randomNumeric) {
		queueUtils.jmsQueueTemplate.send("sms_message", new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setString("telephone", telephone);
				mapMessage.setString("randomNumeric", randomNumeric);
				return mapMessage;
			}
		});
	}
	
	public static void sendMail(final String mail,final String content) {
		queueUtils.jmsQueueTemplate.send("email_message",new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage message = session.createMapMessage();
				message.setString("content", content);
				message.setString("email", mail);
				log.info("生产了消息("+"content:"+content+";email:"+mail+")");
				return message;
			}
		});
	}
}
