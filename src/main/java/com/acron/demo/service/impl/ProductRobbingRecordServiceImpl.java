package com.acron.demo.service.impl;

import com.acron.demo.dao.ProductRobbingRecordMapper;
import com.acron.demo.entity.ProductRobbingRecord;
import com.acron.demo.service.IProductRobbingRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Acron
 * @ClassName ProductRobbingRecordServiceImpl
 * @Description TODO
 * @since 2019/07/07 14:36
 */
@Service("productRobbingRecordService")
public class ProductRobbingRecordServiceImpl extends ServiceImpl<ProductRobbingRecordMapper, ProductRobbingRecord> implements IProductRobbingRecordService {
}
