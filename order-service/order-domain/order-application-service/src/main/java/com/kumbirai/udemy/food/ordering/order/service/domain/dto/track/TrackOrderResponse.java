package com.kumbirai.udemy.food.ordering.order.service.domain.dto.track;

import com.kumbirai.udemy.food.ordering.common.valueobject.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackOrderResponse
{
	@NotNull
	private final UUID orderTrackingId;
	@NotNull
	private final OrderStatus orderStatus;
	private final List<String> failureMessages;
}
