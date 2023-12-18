package com.generic.retailer.controller;

import com.generic.retailer.model.Item;
import com.generic.retailer.model.Order;
import com.generic.retailer.service.logic.ProductService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/retailer/v0")
@RequiredArgsConstructor
public class ProductController {

  @NonNull
  private final ProductService service;

  @GetMapping(
      value = "/products",
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @CrossOrigin
  public Order getProducts()  {
    return service.getNewOrder();
  }

}
