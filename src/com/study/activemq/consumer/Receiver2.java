package com.study.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Administrator
 */
public class Receiver2 {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageConsumer consumer = session.createConsumer(destination);
        while (true){
            TextMessage msg = (TextMessage) consumer.receive();
            if(msg == null){
                break;
            }
            System.out.println("接收到的消息为："+msg.getText());
        }
        if(connection != null){
            connection.close();
        }

    }
}
