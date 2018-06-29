package com.wangchao.product.server.service;

import com.wangchao.product.server.dataobject.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
