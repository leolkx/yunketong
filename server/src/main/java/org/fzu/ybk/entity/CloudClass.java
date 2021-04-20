package org.fzu.ybk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CloudClass {

    private String className;
    private String teacherName;
    private String grade;
    private String teachingMateria;
    private String school;
    private String college;
    private String lessonStartDate;
    private String lessonEndDate;
    private String introduction;

    private Long schoolId;
    private Long collegeId;
    private Long majorId;

    private String orgImage;


}
