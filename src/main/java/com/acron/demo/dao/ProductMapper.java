package com.acron.demo.dao;

import com.acron.demo.entity.database.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Acron
 * @ClassName ProductMapper
 * @Description TODO
 * @since 2019/07/06 20:02
 */
@Repository("productMapper")
public interface ProductMapper extends BaseMapper<Product> {

}
