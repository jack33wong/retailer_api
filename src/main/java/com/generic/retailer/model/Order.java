package com.generic.retailer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @JsonProperty("_id")
  String id;
  LocalDate date;
  double total;
  List<Item> discounts;
  List<Item> trolley;
  String receipt;
}
