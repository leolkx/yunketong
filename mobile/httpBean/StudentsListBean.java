package com.example.YunKeTong.httpBean;

import java.util.List;

public class StudentsListBean extends DefaultResultBean<List<StudentsListBean>>{

    /**
     * id : ？
     * uid : 用户id
     * stu_code : 学号
     * name : 姓名，如李杰铃
     * gender : 性别，如男
     * school : 学校，如福州大学
     * department : 院系，如数计学院
     * profession : 专业，如计算机技术
     * phone : 手机号
     * lack_count : 缺席次数？
     * check_count：签到次数
     */

    private String id;
    private String name;
    private String check_count;

    private String uid;
    private String stu_code;
    private String gender;
    private String school;
    private String department;
    private String profession;
    private String phone;
    private String lack_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStu_code() {
        return stu_code;
    }

    public void setStu_code(String stu_code) {
        this.stu_code = stu_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLack_count() {
        return lack_count;
    }

    public String getCheck_count() {
        return check_count;
    }

    public void setCheck_count(String check_count) {
        this.check_count = check_count;
    }

    public void setLack_count(String lack_count) {
        this.lack_count = lack_count;
    }
}
