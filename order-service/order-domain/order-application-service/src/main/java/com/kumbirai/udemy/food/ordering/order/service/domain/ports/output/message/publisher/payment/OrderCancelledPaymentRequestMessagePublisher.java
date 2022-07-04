package com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.message.publisher.payment;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent>
{
}
