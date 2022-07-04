package com.kumbirai.udemy.food.ordering.domain.exception;

import com.kumbirai.udemy.food.ordering.common.exception.DomainException;

public class OrderDomainException extends DomainException
{
	public OrderDomainException(String message)
	{
		super(message);
	}

	public OrderDomainException(String message, Throwable cause)
	{
		super(message,
				cause);
	}
}
