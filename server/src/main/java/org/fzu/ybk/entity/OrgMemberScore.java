package org.fzu.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgMemberScore {

    private Long userId;
    private String username;
    private Long studentId;

    @TableField(exist = false)
    private int sumScore;//是否参与
    private Long orgId;
    private Long orgCode;


    private Long richTextId;
    //private String richText;



}
