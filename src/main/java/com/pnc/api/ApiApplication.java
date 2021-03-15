package com.pnc.api;

import kafka.Consumer;
import kafka.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.Random;

@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {

		SpringApplication.run(ApiApplication.class, args);
		getDataFromKafka();
	}

	private static void getDataFromKafka() {
		Consumer consumer = new Consumer();
		for (int j = 0; j < 3; j++) {
			String accountNumber =  generateAccountNumber();
			Producer producer = new Producer(new BigDecimal(10000),accountNumber);

			for (int i = 0; i < 2; i++) {
				producer.send("transaction",i);
				producer.send("balance",i);
			}
		}

		consumer.start();
	}

	private static String  generateAccountNumber() {
		Random r = new Random();
		String accountNumber = "";
		for(int i = 0; i < 3; i++)
		{
			accountNumber +=  (char)(r.nextInt(26) + 'a');
		}
		return accountNumber;
	}

}
