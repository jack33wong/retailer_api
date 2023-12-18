package com.generic.retailer.repository.dao;

import com.generic.retailer.model.Order;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDAO {
  @NonNull
  private OrderRepo  orderRepo;

  public Order createOrder(Order toUpdateOrder) {
    toUpdateOrder.setId("");
    return orderRepo.save(toUpdateOrder);
  }

  public List<Order> findAll(){
    return orderRepo.findAll();
  }

}
