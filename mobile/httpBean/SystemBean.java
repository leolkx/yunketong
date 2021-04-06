package com.example.YunKeTong.httpBean;


import java.util.List;

    /**
     * id : ？
     * experience : 用户id
     * uid : 用户id
     */
public class SystemBean extends DefaultResultBean<List<SystemBean>>{
    private String id; // ?
    private String experience; // ?

    private String uid;

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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    }
