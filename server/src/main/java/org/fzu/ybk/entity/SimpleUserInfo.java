package org.fzu.ybk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 该实体描述了一个用户的简单描述，仅包含用户的非敏感信息
 * @author: Mu.xx
 * @date: 2020/4/15 14:44
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserInfo {
    private Long id;
    private String userName;
    private String nickname;
    private String gender;
    private String studentId;
    private String school,education,major;
    private String birthDate;
    private String address,city,province,nation;
    private String profilePhotoUrl;
    private String college;


}
