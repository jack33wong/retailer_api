package com.generic.retailer.util;

public final class ReportFormatter {
  public static String lineSep() {
  return System.lineSeparator();
}

  public static String getPrettyItem(String name){
    return String.format("%-13s",name);
  }

  public static String getPrettyPrice(double price){
    return String.format("%7s","£" + String.format("%.2f",price));
  }

  public static String getPrettyDiscountPrice(double price){
    return String.format("%7s","-£" + String.format("%.2f",price));
  }
}
