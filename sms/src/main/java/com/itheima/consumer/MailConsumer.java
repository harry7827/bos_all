package com.itheima.consumer;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.springframework.stereotype.Component;
import com.itheima.utils.MailUtils;

@Component
public class MailConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage=(MapMessage) message;
		try {
			String content = mapMessage.getString("content");
			String email = mapMessage.getString("email");
			MailUtils.sendMail("速运快递账号激活",content,email);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
