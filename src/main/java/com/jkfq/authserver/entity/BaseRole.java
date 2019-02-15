package com.jkfq.authserver.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @Author
 * @create 2019-01-31
 **/
@Data
public class BaseRole implements Serializable {

    private static final long serialVersionUID = 1647434871739956303L;

    private Long id;
    // 角色名称
    private String roleName;
    // 角色编码
    private String roleCode;

    private List<BaseResource> resources;

}
