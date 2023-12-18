package com.generic.retailer.repository.dao;

import com.generic.retailer.model.Order;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<Order, Integer> {

  List<Order> findAll(Sort sort);

  @Query("{'order_id' : ?0}")
  List<Order> findByOrderId(String orderId);


}
