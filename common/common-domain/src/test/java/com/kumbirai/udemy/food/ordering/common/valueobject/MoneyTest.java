package com.kumbirai.udemy.food.ordering.common.valueobject;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTest
{
	@Test
	void test_isGreaterThanZero()
	{
		Money zero = new Money(new BigDecimal("0.00"));
		Money one = new Money(new BigDecimal("1.00"));
		assertFalse(zero.isGreaterThanZero());
		assertTrue(one.isGreaterThanZero());
	}

	@Test
	void test_isGreaterThan()
	{
		Money zero = new Money(new BigDecimal(0));
		Money one = new Money(new BigDecimal(1));
		assertTrue(one.isGreaterThan(zero));
	}

	@Test
	void test_add()
	{
		Money zero = new Money(new BigDecimal(0));
		Money one = new Money(new BigDecimal(1));
		Money result = zero.add(one);
		assertEquals(new BigDecimal("1.00"),
				result.getAmount());
	}

	@Test
	void test_subtract()
	{
		Money one = new Money(new BigDecimal(1));
		Money result = one.subtract(one);
		assertEquals(new BigDecimal("0.00"),
				result.getAmount());
	}

	@Test
	void test_multiply()
	{
		Money zero = new Money(new BigDecimal(0));
		Money one = new Money(new BigDecimal(1));
		Money result = zero.multiply(7);
		assertEquals(new BigDecimal("0.00"),
				result.getAmount());
		result = one.multiply(7);
		assertEquals(new BigDecimal("7.00"),
				result.getAmount());
	}
}
