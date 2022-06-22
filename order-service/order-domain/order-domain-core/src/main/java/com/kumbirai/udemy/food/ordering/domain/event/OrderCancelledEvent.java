package com.kumbirai.udemy.food.ordering.domain.event;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent
{
	private final DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher;

	public OrderCancelledEvent(Order order, ZonedDateTime createdAt, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher)
	{
		super(order,
				createdAt);
		this.orderCancelledEventDomainEventPublisher = orderCancelledEventDomainEventPublisher;
	}

	@Override
	public void fire()
	{
		orderCancelledEventDomainEventPublisher.publish(this);
	}
}
