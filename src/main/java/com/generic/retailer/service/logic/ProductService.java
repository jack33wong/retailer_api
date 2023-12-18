package com.generic.retailer.service.logic;

import com.generic.retailer.model.Item;
import com.generic.retailer.model.Order;
import com.generic.retailer.repository.ProductFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  @NonNull ProductFactory productFactory;

  public Order getNewOrder(){
    return Order.builder().trolley(new ArrayList<Item>(productFactory.getProductItems().values())).date(
        LocalDate.now() ).build();
  }

}
