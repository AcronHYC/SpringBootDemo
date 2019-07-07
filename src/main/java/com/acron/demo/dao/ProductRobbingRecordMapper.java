package com.acron.demo.dao;

import com.acron.demo.entity.ProductRobbingRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Acron
 * @ClassName ProductRobbingRecordMapper
 * @Description TODO
 * @since 2019/07/07 14:35
 */
@Repository("productRobbingRecordMapper")
public interface ProductRobbingRecordMapper extends BaseMapper<ProductRobbingRecord> {
}
