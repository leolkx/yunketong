package com.example.YunKeTong.httpBean;

import java.util.List;

public class CoursesListBean extends DefaultResultBean<List<CoursesListBean>> {


    /**
     * course_id : 课程id
     * course_name : 课程名
     * teacher : 任课教师
     * time : 上课时间
     */

    private int course_id;
    private String course_name;
    private String teacher;
    private String time;

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
