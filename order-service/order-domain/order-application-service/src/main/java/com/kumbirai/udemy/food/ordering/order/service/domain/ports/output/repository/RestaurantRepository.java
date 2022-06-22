package com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository;

import com.kumbirai.udemy.food.ordering.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository
{
	Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
