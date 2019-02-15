package com.jkfq.authserver.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
@Data
public class BaseUser implements Serializable {
    private static final long serialVersionUID = -5352210702181315632L;

    private Long id;
    private String userName;
    private String userPassword;
    private String displayName;
    private String telephone;
    private String mobile;
    private Date login_time;
    private Date last_login_time;
    private String create_user;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date create_time;
    private String update_user;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date update_time;
    private String del_flag;
    private String is_active;

}
