package com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository;

import com.kumbirai.udemy.food.ordering.domain.entity.Order;
import com.kumbirai.udemy.food.ordering.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository
{
	Order save(Order order);

	Optional<Order> findByTrackingId(TrackingId trackingId);
}
