package com.wht.hello.bean;

import javax.validation.constraints.NotNull;

public class User {

    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String  username;

    /**
     * 密码
     */
    private String  password;

    /**
     * 班主任
     * 
     * @see com.wht.hello.bean.Teacher
     */
    private Teacher teacher;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
