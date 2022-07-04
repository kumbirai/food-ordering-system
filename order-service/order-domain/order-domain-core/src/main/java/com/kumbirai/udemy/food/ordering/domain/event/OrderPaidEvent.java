package com.kumbirai.udemy.food.ordering.domain.event;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent
{
	private final DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher;

	public OrderPaidEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher)
	{
		super(order,
				createdAt);
		this.orderPaidEventDomainEventPublisher = orderPaidEventDomainEventPublisher;
	}

	@Override
	public void fire()
	{
		orderPaidEventDomainEventPublisher.publish(this);
	}
}
