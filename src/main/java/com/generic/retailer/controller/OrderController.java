package com.generic.retailer.controller;

import com.generic.retailer.model.Order;
import com.generic.retailer.service.logic.OrderService;
import com.generic.retailer.service.logic.ProductService;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/retailer/v0")
@RequiredArgsConstructor
public class OrderController {

  @NonNull
  private final OrderService orderService;

  @PostMapping(
      value = "/order/checkout",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @ResponseStatus(HttpStatus.OK)
  @CrossOrigin
  public Order orderCheckout(@RequestBody Order order,  @RequestParam Optional<Boolean> receipt)  {

    log.info("order {}", order);
    if(order.getTrolley() == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "trolley is empty");
    if(order.getDate() == null)
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order date is empty");

    return orderService.processOrder(order,receipt);
  }

  @GetMapping(
    value = "/order/history",
    produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @CrossOrigin
  public List<Order> getOrders()  {
  return orderService.getAllOrder();
}

}
