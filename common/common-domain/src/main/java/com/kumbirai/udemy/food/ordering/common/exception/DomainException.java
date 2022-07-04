package com.kumbirai.udemy.food.ordering.common.exception;

public class DomainException extends RuntimeException
{
	public DomainException(String message)
	{
		super(message);
	}

	public DomainException(String message, Throwable cause)
	{
		super(message,
				cause);
	}
}
