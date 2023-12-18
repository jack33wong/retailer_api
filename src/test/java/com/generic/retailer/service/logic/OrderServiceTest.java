package com.generic.retailer.service.logic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.generic.retailer.config.DiscountConfig;
import com.generic.retailer.model.Item;
import com.generic.retailer.model.Order;
import com.generic.retailer.repository.dao.OrderDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@SpringBootTest
@Slf4j
class OrderServiceTest {

  OrderService orderService;
  @Mock
  private ExchangeFunction exchangeFunction;

  @Autowired
  private DiscountConfig discountConfig;

  @Autowired
  private OrderDAO orderDAO;

  @BeforeEach
  void setUp() {
    orderDAO =  mock(OrderDAO.class);
    orderService = new OrderService(discountConfig,orderDAO);
  }

  @Test
  void when_thurs_dvd_discount_return_27(){


    List<Item> trolley = new ArrayList();
    trolley.add(Item.builder().name("dvd").price(15d).quantity(3).build());
    Order order = Order.builder().date(LocalDate.parse("2023-12-14")).trolley(trolley).build();
    when(orderDAO.createOrder(any())).thenReturn(order);

    order = orderService.processOrder(order, Optional.empty());

    assertThat(order.getTotal()).isEqualTo(27);
    assertThat(order.getDiscounts().size()).isEqualTo(2);

  }

  @Test
  void when_two_book_no_discount_return_ten(){

    List<Item> trolley = new ArrayList();
    trolley.add(Item.builder().name("book").price(5d).quantity(2).build());
    Order order = Order.builder().date(LocalDate.parse("2023-12-17")).trolley(trolley).build();
    when(orderDAO.createOrder(any())).thenReturn(order);

    order = orderService.processOrder(order, Optional.empty());

    assertThat(order.getTotal()).isEqualTo(10);
    assertThat(order.getDiscounts().size()).isEqualTo(0);

  }


}