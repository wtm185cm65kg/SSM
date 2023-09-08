package com.zzk.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;
    private String cardType;
    private String cardNo;
    private String userName;
    private Character userSex;
    private Integer userAge;
    private String userRole;

    public User() {}

    public User(String userId, String cardType, String cardNo, String userName, Character userSex, Integer userAge, String userRole) {
        this.userId = userId;
        this.cardType = cardType;
        this.cardNo = cardNo;
        this.userName = userName;
        this.userSex = userSex;
        this.userAge = userAge;
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", cardType='" + cardType + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", userName='" + userName + '\'' +
                ", userSex=" + userSex +
                ", userAge=" + userAge +
                ", userRole='" + userRole + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Character getUserSex() {
        return userSex;
    }

    public void setUserSex(Character userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
