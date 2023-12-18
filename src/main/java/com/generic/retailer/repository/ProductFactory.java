package com.generic.retailer.repository;

import com.generic.retailer.model.Item;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@EnableConfigurationProperties
@Slf4j
public class ProductFactory {

  private Map<String, Item> productItems = new HashMap<>();

  @Value("#{${products}}")
  private Map<String, Integer> products;

  @PostConstruct
  public void loadComplete(){
    products.forEach((key, value) ->
        productItems.put(key.toUpperCase(), Item.builder().name(key.toUpperCase()).price(NumberUtils.createDouble(value.toString())).build())
    );
  }

}