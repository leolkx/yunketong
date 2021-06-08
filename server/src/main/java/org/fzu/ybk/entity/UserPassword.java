package org.fzu.ybk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/5/22 15:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName(value = "user")
public class UserPassword {
    private Long id;
    private String newPassword;

    @TableField(exist = false)
    private String oldPassword;

    private String phone;

    @TableField(exist = false)
    private String verificationCode;
}
