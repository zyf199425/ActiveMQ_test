package com.study.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.omg.PortableInterceptor.ACTIVE;

import javax.jms.*;

/**
 * @author ZhaoYunFei
 * @date 2018/01/25
 */
public class Receiver3 {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("zyf",
                "zyf","tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageConsumer consumer = session.createConsumer(destination);
        while(true){
            TextMessage msg = (TextMessage) consumer.receive();
            msg.acknowledge();
            if(msg == null){
                break;
            }
            System.out.println("接收到的消息为："+msg.getText());
        }
    }
}
