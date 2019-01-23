package com.kakiridev.simpleenglishwords;

public class User {

    public String userId;
    public String userName;
    public String userEmail;
    public String userScore;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userScore) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userScore = userScore;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    public Integer getUserScoreToInt() {
        return Integer.parseInt(userScore);
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
