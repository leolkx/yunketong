package org.fzu.ybk.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName(value = "participate_in_activity")
public class AttendActivity {
    @TableId( type = IdType.ASSIGN_ID )
    private Long id;
    private Long activityId;
    private Long activityTypeId;
    private Long userId;
    private Long groupId;
    private String submitParam;
    private String submitFileName;
    private String submitFileUrl;
    private Integer editTimes;
    private Integer score;
    private Boolean valid;

    @TableField(exist = false)
    private String answer;

    @TableField(exist = false)
    private String latitude;

    @TableField(exist = false)
    private String longitude;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private Date creationDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private Date lastModificationDate;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
     private Boolean isDeleted;

}
