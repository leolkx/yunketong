package org.fzu.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityParticipateState {

    private Long userId;
    private Long roleId;
    private String username;
    private Long studentId;
    private Long orgId;//班课id
    private Long orgCode;//班课码

    @TableField(exist = false)
    private String isPar;//是否参与

    private Long activityId;

    private int score;

    private Long parId;

}
