package com.yangxin.demo.model;

import java.util.Date;

public class YxUser{
    private Integer id;

    private String username;

    private String password;

    private Date lastVisitTime;

    private String email;

    private Integer activation;

    private Date createTime;

    private boolean x;

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }

    private int sex;

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getActivation() {
        return activation;
    }

    public void setActivation(Integer activation) {
        this.activation = activation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public YxUser() {
    }

    public YxUser(Integer id, String username, String password, String email, int sex, boolean x) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sex = sex;
        this.x = x;
    }

    @Override
    public String toString() {
        return "YxUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastVisitTime=" + lastVisitTime +
                ", email='" + email + '\'' +
                ", activation=" + activation +
                ", createTime=" + createTime +
                '}';
    }
}