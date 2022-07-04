package com.kumbirai.udemy.food.ordering.domain;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;
import com.kumbirai.udemy.food.ordering.domain.entity.Restaurant;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCancelledEvent;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCreatedEvent;
import com.kumbirai.udemy.food.ordering.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService
{
	OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher);

	OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher);

	void approveOrder(Order order);

	OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher);

	void cancelOrder(Order order, List<String> failureMessages);
}
