package com.acron.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Acron
 * @ClassName OrderDto
 * @Description TODO
 * @since 2019/07/10 22:27
 */
@Data
public class OrderDto implements Serializable {
    private String userID;

    private String productID;
}
