package com.wangchao.product.service;

import com.netflix.discovery.converters.Auto;
import com.wangchao.product.dataobject.ProductInfo;
import com.wangchao.product.enums.ProductStatusEnum;
import com.wangchao.product.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}
