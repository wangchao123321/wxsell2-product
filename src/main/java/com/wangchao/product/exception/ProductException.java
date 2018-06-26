package com.wangchao.product.exception;

import com.wangchao.product.enums.ResultEnum;

public class ProductException extends RuntimeException {


    private Integer code;

    public ProductException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
    }

}
