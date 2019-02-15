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
public class BaseResource implements Serializable {

    private static final long serialVersionUID = 1227373699539221963L;

    private Long id;
    private String resource_name;
    private String resource_code;
    private String resource_uri;
    private Long parent_id;
    private String http_method;
    private Integer sort_num;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date careate_time;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date update_time;
    // 是否有操作权限
    private String is_operate;
    private String is_menu;
}
