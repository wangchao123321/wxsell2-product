package com.wangchao.product.service;

import com.wangchao.product.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = productCategoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        System.out.println(byCategoryTypeIn.size());
    }
}