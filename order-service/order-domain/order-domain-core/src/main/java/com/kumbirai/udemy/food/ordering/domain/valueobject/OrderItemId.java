package com.kumbirai.udemy.food.ordering.domain.valueobject;

import com.kumbirai.udemy.food.ordering.common.valueobject.BaseId;

public class OrderItemId extends BaseId<Long>
{
	public OrderItemId(Long value)
	{
		super(value);
	}
}
