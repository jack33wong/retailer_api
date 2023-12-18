package com.generic.retailer.service.logic;

import static java.util.stream.Collectors.groupingBy;

import com.generic.retailer.config.DiscountConfig;
import com.generic.retailer.model.Item;
import com.generic.retailer.model.Order;
import com.generic.retailer.repository.dao.OrderDAO;
import com.generic.retailer.util.ReportFormatter;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  @NonNull
  private DiscountConfig discountConfig;
  @NonNull
  private OrderDAO orderDAO;

  public Order processOrder(Order order, Optional<Boolean> receipt) {

    order.setTrolley(processItem(order));
    order.setDiscounts(getDiscount(order));
    order.setTotal(getTotal(order));
    if(receipt.isPresent() && receipt.get())
      order.setReceipt(getOrderReceipt(order));
    order = orderDAO.createOrder(order);
    return order;
  }

  public String getOrderReceipt(Order order) {

    StringBuilder receiptSB = new StringBuilder();
    receiptSB.append("===== RECEIPT ======").append(ReportFormatter.lineSep())
    .append(printItemList(order))
    .append(printDiscount(order))
    .append("====================").append(ReportFormatter.lineSep())
    .append(ReportFormatter.getPrettyItem("TOTAL")).append(ReportFormatter.getPrettyPrice(order.getTotal()));

    return receiptSB.toString();
  }

  public double getTotal(Order order){

    return order.getTrolley().stream().mapToDouble(item -> item.getSubtotal()).sum() - order.getDiscounts().stream().mapToDouble(item -> item.getSubtotal()).sum();
  }

  public List<Item> getDiscount(Order order) {

    List<Item> discountList = new ArrayList<>();
    Optional<Item> dvdDiscount = getDVDDiscount(order);
    if(dvdDiscount.isPresent()) {
      discountList.add(dvdDiscount.get());
    }

    Optional<Item>  thurDiscount = getThursdayDiscount(order);
    if(thurDiscount.isPresent()) {
      discountList.add(thurDiscount.get());
    }
    return discountList;
  }

  public List<Item> processItem(Order order){

    Map<String, List<Item>> itemMap = order.getTrolley().stream().collect(groupingBy(Item::getName));
    List<Item> itemList = new ArrayList<>();
    itemMap.entrySet().forEach(
        itemGroup -> {
          List<Item> items = itemGroup.getValue();
          itemList.add(Item.builder()
              .name(itemGroup.getKey())
              .price(items.stream().mapToDouble(p -> p.getPrice()).findFirst().orElse(0d) )
              .quantity(items.stream().mapToLong(p -> p.getQuantity()).sum())
              .subtotal(items.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum()).build()
          );
        }
    );
    return itemList;
  }

  public String printItemList(Order order) {

    StringBuilder itemSB = new StringBuilder();

    order.getTrolley().forEach(
        item -> {
          itemSB
              .append(ReportFormatter.getPrettyItem(item.getName() + " " + (item.getQuantity() == 1 ? "" : String.format("(x%s)", item.getQuantity()))))
              .append(ReportFormatter.getPrettyPrice(item.getSubtotal()))
              .append(ReportFormatter.lineSep());
        }
    );
    return itemSB.toString();
  }

  public String printDiscount(Order order) {

    StringBuilder discountSB = new StringBuilder();

    order.getDiscounts().stream().forEach(d -> {
      discountSB.
          append(ReportFormatter.getPrettyItem(d.getName()))
          .append(ReportFormatter.getPrettyDiscountPrice(d.getPrice()))
          .append(ReportFormatter.lineSep());
    });

    return discountSB.toString();
  }

  public Optional<Item> getDVDDiscount(Order order) {

    List<Item> filterDVD = order.getTrolley().stream().filter(dvd -> "dvd".equals(dvd.getName().toLowerCase())).collect(Collectors.toList());
    long dvdCount = filterDVD.stream().mapToLong(qty -> qty.getQuantity()).sum();
    double dvdPrice = filterDVD.stream().mapToDouble(p -> p.getPrice()).findFirst().orElse(0d);
    double discount = dvdPrice * (dvdCount / 2);
    return dvdCount >= 2 ? Optional.of(Item.builder().name(discountConfig.getDISCOUNT_DVD()).price(discount).subtotal(discount).build()) : Optional.empty();

  }

  public Optional<Item> getThursdayDiscount(Order order) {

    boolean isThur = order.getDate().getDayOfWeek().equals(DayOfWeek.THURSDAY) ? true : false;
    Optional<Item> dvdDiscount = getDVDDiscount(order);
    double dvdDiscountValue = dvdDiscount.isPresent() ? dvdDiscount.get().getSubtotal() * 2 : 0d;
    double itemTotal = order.getTrolley().stream().mapToDouble(p -> p.getSubtotal()).sum();
    double discount = (itemTotal - dvdDiscountValue) * 0.2;

    return isThur ? Optional
        .of(Item.builder().name(discountConfig.getDISCOUNT_THURS()).price(discount).subtotal(discount).build())
        : Optional.empty();

  }

  public List<Order> getAllOrder(){
    return orderDAO.findAll();
  }

}
