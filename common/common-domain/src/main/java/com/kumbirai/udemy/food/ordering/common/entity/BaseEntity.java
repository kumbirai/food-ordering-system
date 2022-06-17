package com.kumbirai.udemy.food.ordering.common.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public abstract class BaseEntity<ID>
{
	private ID id;
}
