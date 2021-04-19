package org.fzu.ybk.entity;

/**
 * @description: 该实体描述了用户可更新的字段，外部可见
 * @author: Mu.xx
 * @date: 2020/4/9 23:50
 */
public class UserUpdate {
    private String nickname;
    private String gender;
    private String profilePhotoUrl;
    private String studentId;
    private String school;
    private String major;
    private String education;
    private String college;
    private String birthDate;
    private String address,city,province,nation;


    public void setNickname(String nickName) {
        this.nickname = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getSchool() {
        return school;
    }

    public String getMajor() {
        return major;
    }

    public String getEducation() {
        return education;
    }

    public String getCollege() {
        return college;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getNation() {
        return nation;
    }
}
