package org.fzu.ybk.entity;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllUserInfo {
    private Long id;
    private Long roleId;
    private String username;
    private String nickname;
    private String gender;
    private String studentId;
    private String phone;
    private String email;
    private String school,education,major;
    private String birthDate;
    private String address,city,province,nation;
    private Integer experience,coin;
    private String profilePhotoUrl;
    private String college;

}
