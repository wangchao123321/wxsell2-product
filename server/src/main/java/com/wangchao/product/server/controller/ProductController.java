package com.wangchao.product.server.controller;

import com.wangchao.product.server.dataobject.ProductCategory;
import com.wangchao.product.server.dataobject.ProductInfo;
import com.wangchao.product.server.dto.CartDTO;
import com.wangchao.product.server.service.ProductCategoryService;
import com.wangchao.product.server.service.ProductService;
import com.wangchao.product.server.utils.ResultVOUtil;
import com.wangchao.product.server.vo.ProductInfoVO;
import com.wangchao.product.server.vo.ProductVO;
import com.wangchao.product.server.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 1 查询所有在架商品
     * 2 获取类目type列表
     * 3 查询类目
     * 4 构造数据
     */
    @GetMapping("/list")
     public ResultVO<ProductVO> list(){
        // 1 查询所有在架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 2 获取类目type列表
        List<Integer> categoryTypeList=productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        // 3 查询类目
        List<ProductCategory> categoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        // 4 构造数据
        List<ProductVO> productVOList =new ArrayList<>();
        for (ProductCategory productCategory : categoryList) {
            ProductVO productVO =new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOSList(productInfoVOList);

            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    @PostMapping("/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody  List<String> productIdList){
        return productService.findList(productIdList);
    }

    @GetMapping("/decreaseStock")
    public void decreaseStock(){
        CartDTO cartDTO=new CartDTO("123457",1);
        List<CartDTO> cartDTOList=new ArrayList<>();
        cartDTOList.add(cartDTO);
        productService.decreaseStock(cartDTOList);
    }
}
