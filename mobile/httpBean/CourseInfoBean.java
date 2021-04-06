package com.example.YunKeTong.httpBean;

public class CourseInfoBean extends DefaultResultBean<CourseInfoBean> {

    /**
     * course_id : 课程id
     * course_name : 课程名
     * course_code : 课程代码
     * place : 上课地点
     * location : 签到地点
     * time : 上课时间
     * stu_count : 学生人数
     * teacher : 任课教师
     * creater_uid : 课程创建者uid？
     * check_count : 签到次数
     */

    private String course_name;
    private String course_code;
    private String place;
    private String time;
    private int stu_count;
    private String teacher;
    private int creater_uid;
    private int check_count;

    private String course_id;
    private String location;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStu_count() {
        return stu_count;
    }

    public void setStu_count(int stu_count) {
        this.stu_count = stu_count;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getCreater_uid() {
        return creater_uid;
    }

    public void setCreater_uid(int creater_uid) {
        this.creater_uid = creater_uid;
    }

    public int getCheck_count() {
        return check_count;
    }

    public void setCheck_count(int check_count) {
        this.check_count = check_count;
    }
}
