package com.itheima.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsConsumer implements MessageListener {
	private Log log=LogFactory.getLog(SmsConsumer.class);
	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage=(MapMessage) message;
		
		try {
			String telephone = mapMessage.getString("telephone");
			String randomNumeric = mapMessage.getString("randomNumeric");
			log.info("假装发了短信，telephone:"+telephone+"randomNumeric:"+randomNumeric);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		/*boolean flag = false;
		SendSmsResponse sendSmsResponse = null;
		try {
			String telephone = mapMessage.getString("telephone");
			String randomNumeric = mapMessage.getString("randomNumeric");
			try {
				sendSmsResponse=SmsUtils.sendMsg(telephone, randomNumeric);
			} catch (Exception e) {
				System.out.println("");
				log.info(telephone+"注册时,请求失败,发送短信失败");
			}
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				flag=true;
				log.info(telephone+"注册时,请求成功...发送短信成功");
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}*/
	}
}
