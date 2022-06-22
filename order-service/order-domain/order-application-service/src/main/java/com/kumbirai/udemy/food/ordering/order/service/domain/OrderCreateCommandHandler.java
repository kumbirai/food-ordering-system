package com.kumbirai.udemy.food.ordering.order.service.domain;

import com.kumbirai.udemy.food.ordering.domain.event.OrderCreatedEvent;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.CreateOrderResponse;
import com.kumbirai.udemy.food.ordering.order.service.domain.mapper.OrderDataMapper;
import com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreateCommandHandler
{
	private final OrderCreateHelper orderCreateHelper;
	private final OrderDataMapper orderDataMapper;
	private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

	public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper, OrderDataMapper orderDataMapper, OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher)
	{
		this.orderCreateHelper = orderCreateHelper;
		this.orderDataMapper = orderDataMapper;
		this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
	}

	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand)
	{
		OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
		log.info("Order is created with id: {}",
				orderCreatedEvent.getOrder()
						.getId()
						.getValue());
		orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
		return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),
				"Order created successfully");
	}
}
