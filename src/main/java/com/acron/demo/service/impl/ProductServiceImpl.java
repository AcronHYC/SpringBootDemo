package com.acron.demo.service.impl;

import com.acron.demo.dao.ProductMapper;
import com.acron.demo.entity.database.Product;
import com.acron.demo.entity.database.ProductRobbingRecord;
import com.acron.demo.service.IProductRobbingRecordService;
import com.acron.demo.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Acron
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @since 2019/07/06 20:09
 */
@Slf4j
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements IProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private IProductRobbingRecordService productRobbingRecordService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void manageRobbing(String id,String mobile){
        try {
            if(redisTemplate.opsForValue().get(id)==null){
                Product product=productMapper.selectById(id);
                redisTemplate.opsForValue().set(id,product.getAmount(),10,TimeUnit.MINUTES);
            }else{
                    SessionCallback<Object> callback = new SessionCallback<Object>() {
                        @Override
                        public Object execute(RedisOperations operations) throws DataAccessException {
                            redisTemplate.watch(id);
                            int amount=(Integer) redisTemplate.opsForValue().get(id);
                            if(amount>0) {
                                redisTemplate.multi();
                                redisTemplate.opsForValue().set(id, amount - 1,10,TimeUnit.MINUTES);
                                log.info("手机号：{}抢单成功!", mobile);
                                ProductRobbingRecord record = ProductRobbingRecord.build(mobile, id, new Date());
                                redisTemplate.opsForList().rightPush("recordList",record);
                                redisTemplate.exec();
                                return operations.exec();
                            }else{
                                log.info("商品数量为0，抢购失败！");
                                Product product=productMapper.selectById(id);
                                if(product.getAmount()>0) {
                                    product.setAmount(0);
                                    productMapper.updateById(product);
                                }
                                List<ProductRobbingRecord> recordList=redisTemplate.opsForList().range("recordList",0,-1);
                                boolean flag=productRobbingRecordService.saveBatch(recordList);
                                if(flag){
                                    redisTemplate.delete("recordList");
                                }
                                return true;
                            }
                        }
                    };
                    redisTemplate.execute(callback);
            }

            /*Product product=productMapper.selectById(id);
            if(product!=null && product.getAmount()>0){
                product.setAmount(product.getAmount()-1);
                int result=productMapper.updateById(product);
                if(result>0) {
                    ProductRobbingRecord record = ProductRobbingRecord.build(mobile, id, new Date());
                    productRobbingRecordMapper.insert(record);
                }
            }*/
        }catch (Exception e){
            log.error("处理订单发生异常：",e.fillInStackTrace());
        }
    }
}
