package org.fzu.ybk.entity;

public class ClassMember {
    private Long userId;
    private String userName;
    private Long richTextId;
    private Integer userClassExp;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setRichTextId(Long richTextId) {
        this.richTextId = richTextId;
    }

    public void setUserClassExp(Integer userClassExp) {
        this.userClassExp = userClassExp;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getRichTextId() {
        return richTextId;
    }

    public Integer getUserClassExp() {
        return userClassExp;
    }
}
