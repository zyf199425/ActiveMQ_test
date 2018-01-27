package com.study.activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;

import javax.jms.*;

/**
 * @author ZhaoYunFei
 * @date 2018/01/25
 */
public class Sender3 {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("zyf",
                "zyf","tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
//        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //开启事务
        Session session = connection.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i = 1; i < 6; i++) {
            TextMessage textMessage = session.createTextMessage("要发送的消息;MessageId=" + i);
            producer.send(textMessage);
            session.commit();
        }
        if(connection != null){
            connection.close();
        }

    }
}
