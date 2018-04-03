package com.example.huitong.schoolbusinfoupload.enity;

/**
 * Created by yinxu on 2018/4/3.
 */

public class Student {
    private String name;
    private String phone;
    private boolean status=false;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
