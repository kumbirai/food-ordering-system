package com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.message.publisher.payment;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent>
{
}
