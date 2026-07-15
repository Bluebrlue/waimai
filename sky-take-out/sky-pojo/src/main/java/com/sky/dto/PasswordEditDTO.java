package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 密码修改DTO
 */
@Data
@ApiModel(description = "密码修改DTO")
public class PasswordEditDTO implements Serializable {

    @ApiModelProperty("员工id")
    private Long empId;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}
