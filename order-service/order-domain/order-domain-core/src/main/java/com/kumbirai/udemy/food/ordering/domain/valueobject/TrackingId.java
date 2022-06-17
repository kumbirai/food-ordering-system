package com.kumbirai.udemy.food.ordering.domain.valueobject;

import com.kumbirai.udemy.food.ordering.common.valueobject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID>
{
	public TrackingId(UUID value)
	{
		super(value);
	}
}
