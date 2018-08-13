package com.wht.hello.bean;

public class Teacher {

    /**
     * 主键
     */
    private String uid;

    /**
     * 管理班级
     */
    private String teachClass;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTeachClass() {
        return teachClass;
    }

    public void setTeachClass(String teachClass) {
        this.teachClass = teachClass;
    }
}
