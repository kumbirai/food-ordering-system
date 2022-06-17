package com.kumbirai.udemy.food.ordering.common.valueobject;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Money
{
	public static final Money ZERO = new Money(BigDecimal.ZERO);
	private final BigDecimal amount;

	public boolean isGreaterThanZero()
	{
		return this.amount != null && this.amount.compareTo(BigDecimal.ZERO) > 0;
	}

	public boolean isGreaterThan(Money money)
	{
		return this.amount != null && this.amount.compareTo(money.getAmount()) > 0;
	}

	public Money add(Money money)
	{
		return new Money(setScale(this.amount.add(money.amount)));
	}

	public Money subtract(Money money)
	{
		return new Money(setScale(this.amount.subtract(money.amount)));
	}

	public Money multiply(int multiplier)
	{
		return new Money(setScale(this.amount.multiply(new BigDecimal(multiplier))));
	}

	private BigDecimal setScale(BigDecimal input)
	{
		return input.setScale(2,
				RoundingMode.HALF_EVEN);

	}
}
