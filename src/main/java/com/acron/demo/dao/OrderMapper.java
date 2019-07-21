package com.acron.demo.dao;

import com.acron.demo.entity.database.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Acron
 * @ClassName OrderMapper
 * @Description TODO
 * @since 2019/07/10 21:47
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {
}
