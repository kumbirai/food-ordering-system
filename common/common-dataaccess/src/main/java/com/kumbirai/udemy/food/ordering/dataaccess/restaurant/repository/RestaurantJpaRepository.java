package com.kumbirai.udemy.food.ordering.dataaccess.restaurant.repository;

import com.kumbirai.udemy.food.ordering.dataaccess.restaurant.entity.RestaurantEntity;
import com.kumbirai.udemy.food.ordering.dataaccess.restaurant.entity.RestaurantEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId>
{
	Optional<List<RestaurantEntity>> findByRestaurantIdAndProductIdIn(UUID restaurantId, List<UUID> productIds);
}

