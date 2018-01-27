package com.study.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 赵云飞 on 2018/1/24 0024.
 */
//接收者
public class Receiver {
    public static void main(String[] args) throws JMSException {
        //1、创建 ConnectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
        //2、根据ConnectionFactory 得到Connection 对象
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //3、根据 Connection 创建 session 对象
        Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        //4、根据session 对象创建Destination 对象
        Destination destination = session.createQueue("queue1");
        //5、根据session 对象 创建 messageProducer对象
        MessageConsumer consumer = session.createConsumer(destination);
        while (true){
           TextMessage msg = (TextMessage) consumer.receive();
           if(msg == null) break;
            System.out.println("接收到的消息为："+msg.getText());
        }

        if(connection != null){
            connection.close();
        }
    }
}
