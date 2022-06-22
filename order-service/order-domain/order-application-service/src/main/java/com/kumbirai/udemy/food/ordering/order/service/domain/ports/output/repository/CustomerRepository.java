package com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository;

import com.kumbirai.udemy.food.ordering.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository
{
	Optional<Customer> findCustomer(UUID customerId);
}
