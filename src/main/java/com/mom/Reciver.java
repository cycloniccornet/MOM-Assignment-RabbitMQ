package com.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.SpringApplication;

import java.util.Random;


//Nordea
public class Reciver {
    private final static String EXCHANGE_NAME = "fan-exchange";
    private static int offerFromUser;
    private static Random random = new Random();

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(MomApplication.class, args);
        connectQueue();
    }

    public static void connectQueue() throws Exception
    {
        // Same as the producer: tries to create a queue, if it wasn't already created
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Register for an exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // Get the automatically generated qname for this exchange
        String queueName = channel.queueDeclare().getQueue();
        // Bind the exchange to the queue
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" \nNordea: Waiting for offers.");

        // Get notified, if a message for this receiver arrives
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            offerFromUser = Integer.parseInt(message);
            System.out.println(" \nNordea: Received Offer:'" + message + "'\n");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });

        if (offerFromUser == 0 || offerFromUser < 0) { return;}
        if (offerFromUser > 500000) {
            return;
        } else {

            int devideOffer = random.nextInt(5);

            switch (devideOffer) {
                case 1:  monthString = "January";
                    break;
                case 2:  monthString = "February";
                    break;
                case 3:  monthString = "March";
                    break;
                case 4:  monthString = "April";
                    break;
                case 5:  monthString = "May";
            }
        }


    }

}
