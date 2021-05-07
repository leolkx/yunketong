package org.fzu.ybk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/7/2 18:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity_UserState {
    private Long userId;
    private Long activityId;
    private Long activityTypeId;

    private String activityName;
    private String beginDate;
    private String endDate;
    private Long isActive;

//    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date creationDate;

    private String answer;

    @TableField(exist = false)
    private Long answerLength;

    @TableField(exist = false)
    private Long dateCompare;
}
