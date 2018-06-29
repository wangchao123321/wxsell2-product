package com.wangchao.product.service;

import com.wangchao.product.server.dataobject.ProductInfo;
import com.wangchao.product.server.dto.CartDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() {
        List<ProductInfo> products=productService.findUpAll();
        System.out.println(products.size());
    }

    @Test
    public void decreaseStockTest(){
        CartDTO cartDTO=new CartDTO("123457",5);
        List<CartDTO> list=new ArrayList<>();
        list.add(cartDTO);
        productService.decreaseStock(list);
    }
}