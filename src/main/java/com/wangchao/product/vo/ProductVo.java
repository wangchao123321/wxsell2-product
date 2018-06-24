package com.wangchao.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class ProductVo {

    @JsonProperty("name")
    private String categoryName;

    @JsonPropertyOrder("type")
    private Integer categoryType;


}
