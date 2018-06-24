package com.wangchao.product.service;

import com.wangchao.product.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
