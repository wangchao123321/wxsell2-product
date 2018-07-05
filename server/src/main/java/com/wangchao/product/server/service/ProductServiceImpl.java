package com.wangchao.product.server.service;

import com.rabbitmq.tools.json.JSONUtil;
import com.wangchao.product.common.ProductInfoOutput;
import com.wangchao.product.server.dataobject.ProductInfo;
import com.wangchao.product.server.dto.CartDTO;
import com.wangchao.product.server.enums.ProductStatusEnum;
import com.wangchao.product.server.enums.ResultEnum;
import com.wangchao.product.server.exception.ProductException;
import com.wangchao.product.server.repository.ProductInfoRepository;
import com.wangchao.product.server.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override

    public void decreaseStock(List<CartDTO> cartDTOList) {
        List<ProductInfo> productInfoList=decreaseStockProcess(cartDTOList);
        // 发送mq消息

       List<ProductInfoOutput> productInfoOutputs=productInfoList.stream().map(e -> {
            ProductInfoOutput output=new ProductInfoOutput();
            BeanUtils.copyProperties(e,output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputs));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<CartDTO> cartDTOList) {
        List<ProductInfo> productInfoList=new ArrayList<>();
        for (CartDTO cartDTO : cartDTOList) {
            Optional<ProductInfo> productInfoOptional=productInfoRepository.findById(cartDTO.getProductId());

            // 判断商品是否存在
            if(!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo=productInfoOptional.get();
            // 判断库存是否足够
            Integer result=productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}
