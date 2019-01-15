package com.seckkill.domain;

public class UserPassword {

    private Integer id;
    private String encryptPassword;
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncrptPassword() {
        return encryptPassword;
    }

    public void setEncrptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword == null ? null : encryptPassword.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPassword{" +
                "id=" + id +
                ", encryptPassword='" + encryptPassword + '\'' +
                ", userId=" + userId +
                '}';
    }
}