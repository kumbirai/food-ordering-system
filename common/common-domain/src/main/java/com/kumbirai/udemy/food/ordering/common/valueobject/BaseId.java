package com.kumbirai.udemy.food.ordering.common.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class BaseId<T>
{
	private final T value;

	protected BaseId(T value)
	{
		this.value = value;
	}
}
