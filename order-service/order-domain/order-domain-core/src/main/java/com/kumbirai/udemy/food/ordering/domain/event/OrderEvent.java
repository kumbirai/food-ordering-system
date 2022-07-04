package com.kumbirai.udemy.food.ordering.domain.event;

import com.kumbirai.udemy.food.ordering.common.event.DomainEvent;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order>
{
	private final Order order;
	private final ZonedDateTime createdAt;

	public OrderEvent(Order order, ZonedDateTime createdAt)
	{
		this.order = order;
		this.createdAt = createdAt;
	}

	public Order getOrder()
	{
		return order;
	}

	public ZonedDateTime getCreatedAt()
	{
		return createdAt;
	}
}
