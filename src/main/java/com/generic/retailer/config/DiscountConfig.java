package com.generic.retailer.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "discount")
@Accessors(chain = true)
public class DiscountConfig {
  private String DISCOUNT_DVD;
  private String DISCOUNT_THURS;
}
