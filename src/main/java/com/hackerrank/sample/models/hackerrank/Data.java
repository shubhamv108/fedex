package com.hackerrank.sample.models.hackerrank;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data {
  @JsonProperty("barcode")
  private String barcode;
  private String item;
  private String category;
  private Integer price;
  private Integer discount;
  private Integer available;

  public String getBarCode() {
    return this.barcode;
  }

  public Integer getPrice() {
    return this.price;
  }

  public Integer getDiscount() {
    return this.discount;
  }

  public Integer getAvailable() {
    return this.available;
  }
}