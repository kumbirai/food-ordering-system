package com.kumbirai.udemy.food.ordering.order.service.domain;

import com.kumbirai.udemy.food.ordering.common.valueobject.CustomerId;
import com.kumbirai.udemy.food.ordering.common.valueobject.Money;
import com.kumbirai.udemy.food.ordering.common.valueobject.OrderId;
import com.kumbirai.udemy.food.ordering.common.valueobject.OrderStatus;
import com.kumbirai.udemy.food.ordering.common.valueobject.ProductId;
import com.kumbirai.udemy.food.ordering.common.valueobject.RestaurantId;
import com.kumbirai.udemy.food.ordering.domain.entity.Customer;
import com.kumbirai.udemy.food.ordering.domain.entity.Order;
import com.kumbirai.udemy.food.ordering.domain.entity.Product;
import com.kumbirai.udemy.food.ordering.domain.entity.Restaurant;
import com.kumbirai.udemy.food.ordering.domain.exception.OrderDomainException;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.CreateOrderCommand;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.CreateOrderResponse;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.OrderAddress;
import com.kumbirai.udemy.food.ordering.order.service.domain.dto.create.OrderItem;
import com.kumbirai.udemy.food.ordering.order.service.domain.mapper.OrderDataMapper;
import com.kumbirai.udemy.food.ordering.order.service.domain.ports.input.service.OrderApplicationService;
import com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository.CustomerRepository;
import com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository.OrderRepository;
import com.kumbirai.udemy.food.ordering.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest
{
	private final UUID CUSTOMER_ID = UUID.fromString("9250b934-5376-449c-90c5-4737c4fc8d66");
	private final UUID RESTAURANT_ID = UUID.fromString("7ac9fc26-51ae-4021-a2c4-96f372cdbcc5");
	private final UUID PRODUCT_ID = UUID.fromString("1de7040f-8f6e-40d1-a3a3-45b23e955206");
	private final UUID ORDER_ID = UUID.fromString("3a0a1952-3225-468d-9d88-b2bef046da89");
	private final BigDecimal PRICE = new BigDecimal("200.00");
	@Autowired
	private OrderApplicationService orderApplicationService;
	@Autowired
	private OrderDataMapper orderDataMapper;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RestaurantRepository restaurantRepository;
	private CreateOrderCommand createOrderCommand;
	private CreateOrderCommand createOrderCommandWrongPrice;
	private CreateOrderCommand createOrderCommandWrongProductPrice;

	@BeforeAll
	void init()
	{
		createOrderCommand = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("street_1")
						.postalCode("1000AB")
						.city("Paris")
						.build())
				.price(PRICE)
				.items(List.of(OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("50.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()))
				.build();

		createOrderCommandWrongPrice = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("street_1")
						.postalCode("1000AB")
						.city("Paris")
						.build())
				.price(new BigDecimal("250.00"))
				.items(List.of(OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("50.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()))
				.build();

		createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("street_1")
						.postalCode("1000AB")
						.city("Paris")
						.build())
				.price(new BigDecimal("210.00"))
				.items(List.of(OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("60.00"))
								.subTotal(new BigDecimal("60.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()))
				.build();

		Customer customer = new Customer();
		customer.setId(new CustomerId(CUSTOMER_ID));

		Restaurant restaurantResponse = Restaurant.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.products(List.of(new Product(new ProductId(PRODUCT_ID),
								"product-1",
								new Money(new BigDecimal("50.00"))),
						new Product(new ProductId(PRODUCT_ID),
								"product-2",
								new Money(new BigDecimal("50.00")))))
				.active(true)
				.build();

		Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		order.setId(new OrderId(ORDER_ID));

		when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
		when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));
		when(orderRepository.save(any(Order.class))).thenReturn(order);
	}

	@Test
	void testCreateOrder()
	{
		CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
		assertEquals(createOrderResponse.getOrderStatus(),
				OrderStatus.PENDING);
		assertEquals(createOrderResponse.getMessage(),
				"Order created successfully");
		assertNotNull(createOrderResponse.getOrderTrackingId());
	}

	@Test
	void testCreateOrderWithWrongTotalPrice()
	{
		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
				() -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
		BigDecimal totalPrice = new BigDecimal("250.00");
		BigDecimal orderItemsPrice = new BigDecimal("200.00");
		assertEquals(orderDomainException.getMessage(),
				String.format("Total price: %s is not equal to Order items total: %s",
						totalPrice,
						orderItemsPrice));
	}

	@Test
	void testCreateOrderWithWrongProductPrice()
	{
		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
				() -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
		BigDecimal orderItemsPrice = new BigDecimal("60.00");
		assertEquals(orderDomainException.getMessage(),
				String.format("Order item price: %s is not valid for product %s",
						orderItemsPrice,
						PRODUCT_ID));
	}

	@Test
	void testCreateOrderWithPassiveRestaurant()
	{
		Restaurant restaurantResponse = Restaurant.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.products(List.of(new Product(new ProductId(PRODUCT_ID),
								"product-1",
								new Money(new BigDecimal("50.00"))),
						new Product(new ProductId(PRODUCT_ID),
								"product-2",
								new Money(new BigDecimal("50.00")))))
				.active(false)
				.build();
		when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));
		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class,
				() -> orderApplicationService.createOrder(createOrderCommand));
		assertEquals(orderDomainException.getMessage(),
				"Restaurant with id " + RESTAURANT_ID + " is currently not active!");
	}
}
