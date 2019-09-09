package com.yangxin.demo.model;

import java.util.Date;

public class YxUser{
    private Integer id;

    private String username;

    private int age;

    private Date lastVisitTime;

    private String email;

    private Integer activation;

    private Date createTime;

    private boolean x;

    public YxUser(Integer id, String username, int age, String email, Integer activation,boolean x) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.lastVisitTime = lastVisitTime;
        this.email = email;
        this.activation = activation;
        this.x = x;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public boolean isX() {
        return x;
    }

    public void setX(boolean x) {
        this.x = x;
    }
}