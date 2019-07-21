package com.acron.demo.service;

import com.acron.demo.entity.database.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Acron
 * @ClassName IProductService
 * @Description TODO
 * @since 2019/07/06 20:08
 */
public interface IProductService extends IService<Product> {

    /***
      * @Description:处理商城抢单
      * @Date: 2019/7/7
      * @Param mobile: 手机号码
      * @return: void
      **/
    void manageRobbing(String id,String mobile);
}
