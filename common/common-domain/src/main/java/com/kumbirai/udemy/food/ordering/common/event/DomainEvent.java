package com.kumbirai.udemy.food.ordering.common.event;

public interface DomainEvent<T>
{
	void fire();
}
