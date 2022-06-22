package com.kumbirai.udemy.food.ordering.common.event.publisher;

import com.kumbirai.udemy.food.ordering.common.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent>
{
	void publish(T domainEvent);
}
