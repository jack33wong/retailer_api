package com.generic.retailer.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.generic.retailer.service.logic.OrderService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class OrderControllerTest {

  private OrderController orderController;

  private OrderService orderService;
  private MockMvc mockMvc;


  @BeforeEach
  void setUp() {
    orderService = mock(OrderService.class);
    orderController = new OrderController(orderService);
    this.mockMvc = MockMvcBuilders
        .standaloneSetup(new OrderController(orderService))
        .build();
    standaloneSetup(orderController);
  }

  @Test
  void no_trolley_return_error() throws Exception {

    String request =  readFromFileToString("/order/invalid.json");
    mockMvc
        .perform(post("/retailer/v0/order/checkout")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(400));
  }

  @Test
  void valid_order_return_ok() throws Exception {

    String request = readFromFileToString("/order/valid.json");
    mockMvc
        .perform(post("/retailer/v0/order/checkout")
            .content(request)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  private static String readFromFileToString(String filePath) throws IOException {
    File resource = new ClassPathResource(filePath).getFile();
    byte[] byteArray = Files.readAllBytes(resource.toPath());
    return new String(byteArray);
  }

}
