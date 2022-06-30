package com.kumbirai.udemy.food.ordering.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException
{
	public KafkaProducerException(String message)
	{
		super(message);
	}
}
