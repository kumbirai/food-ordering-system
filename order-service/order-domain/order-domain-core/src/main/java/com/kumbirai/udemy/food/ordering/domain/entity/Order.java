package com.kumbirai.udemy.food.ordering.domain.entity;

import com.kumbirai.udemy.food.ordering.common.entity.AggregateRoot;
import com.kumbirai.udemy.food.ordering.common.valueobject.CustomerId;
import com.kumbirai.udemy.food.ordering.common.valueobject.Money;
import com.kumbirai.udemy.food.ordering.common.valueobject.OrderId;
import com.kumbirai.udemy.food.ordering.common.valueobject.OrderStatus;
import com.kumbirai.udemy.food.ordering.common.valueobject.RestaurantId;
import com.kumbirai.udemy.food.ordering.domain.exception.OrderDomainException;
import com.kumbirai.udemy.food.ordering.domain.valueobject.OrderItemId;
import com.kumbirai.udemy.food.ordering.domain.valueobject.StreetAddress;
import com.kumbirai.udemy.food.ordering.domain.valueobject.TrackingId;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order extends AggregateRoot<OrderId>
{
	private final CustomerId customerId;
	private final RestaurantId restaurantId;
	private final StreetAddress deliveryAddress;
	private final Money price;
	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;

	private Order(Builder builder)
	{
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		deliveryAddress = builder.deliveryAddress;
		price = builder.price;
		items = builder.items;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessages = builder.failureMessages;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public void initializeOrder()
	{
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}

	public void validateOrder()
	{
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}

	public void pay()
	{
		if (orderStatus != OrderStatus.PENDING)
		{
			throw new OrderDomainException("Order is not in correct state for pay operation!");
		}
		orderStatus = OrderStatus.PAID;
	}

	public void approve()
	{
		if (orderStatus != OrderStatus.PAID)
		{
			throw new OrderDomainException("Order is not in correct state for approve operation!");
		}
		orderStatus = OrderStatus.APPROVED;
	}

	public void initCancel(List<String> failureMessages)
	{
		if (orderStatus != OrderStatus.PAID)
		{
			throw new OrderDomainException("Order is not in correct state for initCancel operation!");
		}
		orderStatus = OrderStatus.CANCELLING;
		updateFailureMessages(failureMessages);
	}

	public void cancel(List<String> failureMessages)
	{
		if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING))
		{
			throw new OrderDomainException("Order is not in correct state for cancel operation!");
		}
		orderStatus = OrderStatus.CANCELLED;
		updateFailureMessages(failureMessages);
	}

	private void updateFailureMessages(List<String> failureMessages)
	{
		if (Objects.isNull(this.failureMessages) && Objects.nonNull(failureMessages))
		{
			this.failureMessages = new ArrayList<>();
		}

		if (Objects.nonNull(failureMessages))
		{
			this.failureMessages.addAll(failureMessages.stream()
					.filter(message -> !message.isEmpty())
					.collect(Collectors.toList()));
		}
	}

	private void validateInitialOrder()
	{
		if (orderStatus != null || getId() != null)
		{
			throw new OrderDomainException("Order is not in the correct state for initialization!");
		}
	}

	private void validateTotalPrice()
	{
		if (price == null || !price.isGreaterThanZero())
		{
			throw new OrderDomainException("Total price must be greater than zero!");
		}
	}

	private void validateItemsPrice()
	{
		Money orderItemsTotal = items.stream()
				.map(orderItem ->
				{
					validateItemPrice(orderItem);
					return orderItem.getSubTotal();
				})
				.reduce(Money.ZERO,
						Money::add);

		if (!price.equals(orderItemsTotal))
		{
			throw new OrderDomainException(String.format("Total price: %s is not equal to Order items total: %s",
					price.getAmount(),
					orderItemsTotal.getAmount()));
		}
	}

	private void validateItemPrice(OrderItem orderItem)
	{
		if (!orderItem.isPriceValid())
		{
			throw new OrderDomainException(String.format("Order item price: %s is not valid for product %s",
					orderItem.getPrice()
							.getAmount(),
					orderItem.getProduct()
							.getId()
							.getValue()));
		}
	}

	private void initializeOrderItems()
	{
		long itemId = 1;
		for (OrderItem orderItem : items)
		{
			orderItem.initializeOrderItem(this.getId(),
					new OrderItemId(itemId++));
		}
	}

	public CustomerId getCustomerId()
	{
		return customerId;
	}

	public RestaurantId getRestaurantId()
	{
		return restaurantId;
	}

	public StreetAddress getDeliveryAddress()
	{
		return deliveryAddress;
	}

	public Money getPrice()
	{
		return price;
	}

	public List<OrderItem> getItems()
	{
		return items;
	}

	public TrackingId getTrackingId()
	{
		return trackingId;
	}

	public OrderStatus getOrderStatus()
	{
		return orderStatus;
	}

	public List<String> getFailureMessages()
	{
		return failureMessages;
	}

	public static final class Builder
	{
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress deliveryAddress;
		private Money price;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages;

		private Builder()
		{
		}

		public Builder orderId(OrderId val)
		{
			orderId = val;
			return this;
		}

		public Builder customerId(CustomerId val)
		{
			customerId = val;
			return this;
		}

		public Builder restaurantId(RestaurantId val)
		{
			restaurantId = val;
			return this;
		}

		public Builder deliveryAddress(StreetAddress val)
		{
			deliveryAddress = val;
			return this;
		}

		public Builder price(Money val)
		{
			price = val;
			return this;
		}

		public Builder items(List<OrderItem> val)
		{
			items = val;
			return this;
		}

		public Builder trackingId(TrackingId val)
		{
			trackingId = val;
			return this;
		}

		public Builder orderStatus(OrderStatus val)
		{
			orderStatus = val;
			return this;
		}

		public Builder failureMessages(List<String> val)
		{
			failureMessages = val;
			return this;
		}

		public Order build()
		{
			return new Order(this);
		}
	}
}
