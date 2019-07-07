package com.acron.demo.service.impl;

import com.acron.demo.dao.ProductMapper;
import com.acron.demo.dao.ProductRobbingRecordMapper;
import com.acron.demo.entity.Product;
import com.acron.demo.entity.ProductRobbingRecord;
import com.acron.demo.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Acron
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @since 2019/07/06 20:09
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements IProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductRobbingRecordMapper productRobbingRecordMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void manageRobbing(String id,String mobile){
        try {
            Product product=productMapper.selectById(id);
            if(product!=null && product.getAmount()>0){
                product.setAmount(product.getAmount()-1);
                int reuslt=productMapper.updateById(product);
                if(reuslt>0) {
                    ProductRobbingRecord record = ProductRobbingRecord.build(mobile, id, new Date());
                    productRobbingRecordMapper.insert(record);
                }
            }
        }catch (Exception e){
            log.error("处理订单发生异常：",e.fillInStackTrace());
        }
    }
}
