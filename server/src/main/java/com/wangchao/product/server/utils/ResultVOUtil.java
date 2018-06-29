package com.wangchao.product.server.utils;

import com.wangchao.product.server.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(200);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

}
