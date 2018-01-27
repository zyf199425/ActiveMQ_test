package com.study.activemq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author ZhaoYunFei
 * @date 2018/01/25
 */
public class Producer {
    //连接工厂对象
    private ConnectionFactory connectionFactory;
    //连接对象
    private Connection connection;
    //Session 对象
    private Session session;
    //生产者
    private MessageProducer messageProducer;

    public Producer(){
        try {
            this.connectionFactory = new ActiveMQConnectionFactory("zyf","zyf","tcp://localhost:61616");
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.messageProducer = session.createProducer(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Session getSession(){
        return this.session;
    }

    public void send(){
        try {
            Destination destination = this.session.createQueue("queue1");
            MapMessage msg1 = this.session.createMapMessage();
            msg1.setString("name","张三");
            msg1.setString("age","23");
            msg1.setStringProperty("color","red");
            msg1.setIntProperty("sal",10000);
            MapMessage msg2 = this.session.createMapMessage();
            msg2.setString("name","李四");
            msg2.setString("age","25");
            msg2.setStringProperty("color","blue");
            msg2.setIntProperty("sal",16023);
            MapMessage msg3 = this.session.createMapMessage();
            msg3.setString("name","王五");
            msg3.setString("age","20");
            msg3.setStringProperty("color","yellow");
            msg3.setIntProperty("sal",201235);
            MapMessage msg4 = this.session.createMapMessage();
            msg4.setString("name","赵六");
            msg4.setString("age","30");
            msg4.setStringProperty("color","black");
            msg4.setIntProperty("sal",20000);

            this.messageProducer.send(destination,msg1,DeliveryMode.PERSISTENT,2,1000*60*10L);
            this.messageProducer.send(destination,msg2,DeliveryMode.PERSISTENT,3,1000*60*10L);
            this.messageProducer.send(destination,msg3,DeliveryMode.PERSISTENT,9,1000*60*10L);
            this.messageProducer.send(destination,msg4,DeliveryMode.PERSISTENT,6,1000*60*10L);
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
           closeConnection();
        }
    }

    public void closeConnection(){
        if(this.connection != null){
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Producer p = new Producer();
        p.send();
    }
}
