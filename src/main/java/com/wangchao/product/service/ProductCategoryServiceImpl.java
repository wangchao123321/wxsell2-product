package com.wangchao.product.service;

import com.wangchao.product.dataobject.ProductCategory;
import com.wangchao.product.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return productCategoryRepository.findByCategoryTypeIn(categoryType);
    }
}
