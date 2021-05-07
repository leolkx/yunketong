package org.fzu.ybk.entity;

/**
 * @description:
 * @author: Mu.xx
 * @date: 2020/4/9 23:04
 */
public class ClassMemberUpdate {

    private String userClassName;
    private String userClassSchool;
    private String userClassCollege;
    private String userClassMajor;
    private String userClassNumber;
    private String userClassNickName;

    public void setUserClassNickName(String userNickName) {
        this.userClassNickName = userNickName;
    }

    public void setUserClassName(String userClassName) {
        this.userClassName = userClassName;
    }


    public void setUserClassSchool(String userClassSchool) {
        this.userClassSchool = userClassSchool;
    }

    public void setUserClassCollege(String userClassCollege) {
        this.userClassCollege = userClassCollege;
    }

    public void setUserClassMajor(String userClassMajor) {
        this.userClassMajor = userClassMajor;
    }

    public void setUserClassNumber(String userClassNumber) {
        this.userClassNumber = userClassNumber;
    }

    public String getUserClassName() {
        return userClassName;
    }

    public String getUserClassSchool() {
        return userClassSchool;
    }

    public String getUserClassCollege() {
        return userClassCollege;
    }

    public String getUserClassMajor() {
        return userClassMajor;
    }

    public String getUserClassNumber() {
        return userClassNumber;
    }

    public String getUserClassNickName() {
        return userClassNickName;
    }
}
