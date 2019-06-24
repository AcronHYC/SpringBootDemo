package com.acron.demo.entity;

import lombok.Data;

/**
 * @author Acron
 * @since 2019/06/23 17:44
 */
@Data
public class User {
    private String id;
    private String userName;
    private String password;
    private String telephone;
    private String email;
}
