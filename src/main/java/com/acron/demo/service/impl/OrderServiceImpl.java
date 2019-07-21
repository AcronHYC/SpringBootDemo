package com.acron.demo.service.impl;

import com.acron.demo.dao.OrderMapper;
import com.acron.demo.entity.database.Order;
import com.acron.demo.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Acron
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @since 2019/07/10 21:51
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
}
