package com.example.YunKeTong.httpBean;

public class LoginBean extends DefaultResultBean<LoginBean> {


    /**
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtc2ciOiI1LDE1NjE4NzkwNjAwMTUiLCJpYXQiOjE1NjE4NzkwNjAsImV4cCI6MTU2MjIzOTA2MH0.NKbik1Uwynx09AH4__wnqhFiY7GZJkUVfPw0cHOwPeY
     * uid : 用户id
     * email : 邮箱
     * name : 姓名
     * nick_name : 昵称
     * gender : 性别
     * stu_code : 学工号
     * phone : 手机号
     * type : 用户类型，3为学生，2为老师
     * avatar : 头像
     * school : 学校
     * department : 院系
     * profession : 专业
     */

    private String token;
    private int uid;
    private String email;
    private String name;
    private String nick_name; //昵称
    private String gender;
    private String stu_code;
    private String phone;
    private int type; //3为学生，2为老师
    private String avatar; //头像
    private String school;
    private String department;
    private String profession;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStu_code() {
        return stu_code;
    }

    public void setStu_code(String stu_code) {
        this.stu_code = stu_code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

}
