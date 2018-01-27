package com.study.activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by 赵云飞 on 2018/1/24 0024.
 */
//发送者
public class Sender {

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
        MessageProducer messageProducer = session.createProducer(destination);
        //6、使用 messageProducer 对象传送 message
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        //7、根据session 对象创建 TextMessage 对象，发送消息，完成后关闭连接
        for (int i = 1;i <= 5;i++){
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("我是要发送的消息：ID:"+i);
            messageProducer.send(textMessage);
        }

        if(connection != null){
            connection.close();
        }
    }
}
