package com.study.activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 赵云飞 on 2018/1/24 0024.
 */
public class Sender1 {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        for (int i = 1; i <= 5; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是要发送的消息；ID:"+i);
            producer.send(textMessage);
        }
        if(connection != null){
            connection.close();
        }
    }
}
