package com.kumbirai.udemy.food.ordering.domain.exception;

import com.kumbirai.udemy.food.ordering.common.exception.DomainException;

public class OrderNotFoundException extends DomainException
{
	public OrderNotFoundException(String message)
	{
		super(message);
	}

	public OrderNotFoundException(String message, Throwable cause)
	{
		super(message,
				cause);
	}
}
