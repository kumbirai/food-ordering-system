package com.kumbirai.udemy.food.ordering.domain.event;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent
{
	private final DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher;

	public OrderCreatedEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher)
	{
		super(order,
				createdAt);
		this.orderCreatedEventDomainEventPublisher = orderCreatedEventDomainEventPublisher;
	}

	@Override
	public void fire()
	{
		orderCreatedEventDomainEventPublisher.publish(this);
	}
}
