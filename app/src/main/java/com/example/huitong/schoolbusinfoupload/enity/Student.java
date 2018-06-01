package com.example.huitong.schoolbusinfoupload.enity;

/**
 * Created by yinxu on 2018/4/3.
 */

public class Student {
    private Integer id;
    private String name;
    private String phone;
    private String status;
    private String address;
    private String  distance;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
