package com.kumbirai.udemy.food.ordering.domain;

import com.kumbirai.udemy.food.ordering.common.event.publisher.DomainEventPublisher;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;
import com.kumbirai.udemy.food.ordering.domain.entity.Product;
import com.kumbirai.udemy.food.ordering.domain.entity.Restaurant;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCancelledEvent;
import com.kumbirai.udemy.food.ordering.domain.event.OrderCreatedEvent;
import com.kumbirai.udemy.food.ordering.domain.event.OrderPaidEvent;
import com.kumbirai.udemy.food.ordering.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService
{

	public static final String UTC = "UTC";

	@Override
	public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant, DomainEventPublisher<OrderCreatedEvent> orderCreatedEventDomainEventPublisher)
	{
		validateRestaurant(restaurant);
		setOrderProductInformation(order,
				restaurant);
		order.validateOrder();
		order.initializeOrder();
		log.info("Order with id: {} is initiated",
				order.getId()
						.getValue());
		return new OrderCreatedEvent(order,
				ZonedDateTime.now(ZoneId.of(UTC)),
				orderCreatedEventDomainEventPublisher);
	}

	@Override
	public OrderPaidEvent payOrder(Order order, DomainEventPublisher<OrderPaidEvent> orderPaidEventDomainEventPublisher)
	{
		order.pay();
		log.info("Order with id: {} is paid",
				order.getId()
						.getValue());
		return new OrderPaidEvent(order,
				ZonedDateTime.now(ZoneId.of(UTC)),
				orderPaidEventDomainEventPublisher);
	}

	@Override
	public void approveOrder(Order order)
	{
		order.approve();
		log.info("Order with id: {} is approved",
				order.getId()
						.getValue());
	}

	@Override
	public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages, DomainEventPublisher<OrderCancelledEvent> orderCancelledEventDomainEventPublisher)
	{
		order.initCancel(failureMessages);
		log.info("Order payment is cancelling for order id: {}",
				order.getId()
						.getValue());
		return new OrderCancelledEvent(order,
				ZonedDateTime.now(ZoneId.of(UTC)),
				orderCancelledEventDomainEventPublisher);
	}

	@Override
	public void cancelOrder(Order order, List<String> failureMessages)
	{
		order.cancel(failureMessages);
		log.info("Order with id: {} is cancelled",
				order.getId()
						.getValue());
	}

	private void validateRestaurant(Restaurant restaurant)
	{
		if (!restaurant.isActive())
		{
			throw new OrderDomainException("Restaurant with id " + restaurant.getId()
					.getValue() + " is currently not active!");
		}
	}

	private void setOrderProductInformation(Order order, Restaurant restaurant)
	{
		order.getItems()
				.forEach(orderItem -> restaurant.getProducts()
						.forEach(restaurantProduct ->
						{
							Product currentProduct = orderItem.getProduct();
							if (currentProduct.equals(restaurantProduct))
							{
								currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
										restaurantProduct.getPrice());
							}
						}));
	}
}