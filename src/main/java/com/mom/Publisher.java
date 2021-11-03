package com.mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.SpringApplication;

import java.util.Scanner;

public class Publisher {

    //private final static String QUEUE_NAME = "helloqueue";
    // non-durable, exclusive, auto-delete queue with an automatically generated name
    private static String queueName = null;
    private final static String EXCHANGE_NAME = "fan-exchange";

    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(MomApplication.class, args);

        Scanner userInput = new Scanner(System.in);
        System.out.println("\nPlease enter an amount to borrow, you broke fuck:");


        String message = userInput.nextLine();
        createQueue(message);
        System.out.println(" Publisher: Send a request for a loan with the amount - '" + message + "'\n");
    }

    public static void createQueue(String message) throws Exception
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel())
        {
            // channel.queueDeclare(queueName, false, false, false, null);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        }
    }
}

