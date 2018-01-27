package com.study.activemq.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author ZhaoYunFei
 * @date 2018/01/25
 */
public class Consumer {

    private final String SELECT_0 = "sal > 15000";

    private final String SELECT_1 = "color = 'red'";

    private final String SELECT_2 = "age > 23";

    private ConnectionFactory connectionFactory;

    private Connection connection;

    private Session session;

    private Destination destination;

    private MessageConsumer consumer;

    public Consumer(){
        try {
            this.connectionFactory = new ActiveMQConnectionFactory("zyf","zyf","tcp://localhost:61616");
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            this.destination = this.session.createQueue("queue1");
            this.consumer = session.createConsumer(this.destination,SELECT_1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receiver(){
        try {
            this.consumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    class Listener implements MessageListener{

        @Override
        public void onMessage(Message message) {
            try {
                if(message instanceof  TextMessage){
                    System.out.println("TextMessage");
                }
                if(message instanceof  MapMessage){
                    MapMessage msg = (MapMessage) message;
                    System.out.println(msg.toString());
                    System.out.println(msg.getString("name"));
                    System.out.println(msg.getString("age"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receiver();
    }
}
