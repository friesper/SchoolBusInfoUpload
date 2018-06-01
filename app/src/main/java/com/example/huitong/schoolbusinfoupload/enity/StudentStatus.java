package com.example.huitong.schoolbusinfoupload.enity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class StudentStatus  {
    private Integer id;
    private Integer busId;
    private Integer driverId;
    private Integer nurseId;
    private String nurseName;
    private String driverName;
    @JSONField(format = "yyyy-MM-dd")
    private Date takeTime;
    private String studentName;
    private String studentPhone;
    private Integer timeQuantum;
    private String busNumber;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getNurseId() {
        return nurseId;
    }

    public void setNurseId(Integer nurseId) {
        this.nurseId = nurseId;
    }

    public String getNurseName() {
        return nurseName;
    }

    public void setNurseName(String nurseName) {
        this.nurseName = nurseName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public Integer getTimeQuantum() {
        return timeQuantum;
    }

    public void setTimeQuantum(Integer timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public String toString() {
        return "StudentStatus{" +
                "id=" + id +
                ", busId=" + busId +
                ", driverId=" + driverId +
                ", nurseId=" + nurseId +
                ", nurseName='" + nurseName + '\'' +
                ", driverName='" + driverName + '\'' +
                ", takeTime=" + takeTime +
                ", studentName='" + studentName + '\'' +
                ", studentPhone='" + studentPhone + '\'' +
                ", timeQuantum=" + timeQuantum +
                ", busNumber='" + busNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
