package com.example.huitong.schoolbusinfoupload.enity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * Created by yinxu on 2018/3/30.
 */

public class BusInfo {
        private Integer id;
        private  Integer busId;
        private String busNumber;
        private String driverName;
        @JSONField(format = "yyyy-MM-dd")
        private Date createTime;
        private String  engineHygiene;
        private String airFilter;
        private String batteryHealth;
        private String medicineBox;
        private String gpsMonitoring;
        private String fireExtinguisher;
        private String escapeDoor;
        private String safetyHammer;
        private String oillOilLevel;
        private String  amountOfAntifreeze;
        private String bakeFluid;
        private String beltTightness;
        private String tirePressureScrews;
        private String lights;
        private String guideBoard;
        private String clutch;
        private String brake;
        private String steeringWheel;
        private String instrumentPanel;



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

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getEngineHygiene() {
            return engineHygiene;
        }

        public void setEngineHygiene(String engineHygiene) {
            this.engineHygiene = engineHygiene;
        }

        public String getAirFilter() {
            return airFilter;
        }

        public void setAirFilter(String airFilter) {
            this.airFilter = airFilter;
        }

        public String getBatteryHealth() {
            return batteryHealth;
        }

        public void setBatteryHealth(String batteryHealth) {
            this.batteryHealth = batteryHealth;
        }

        public String getMedicineBox() {
            return medicineBox;
        }

        public void setMedicineBox(String medicineBox) {
            this.medicineBox = medicineBox;
        }

        public String getGpsMonitoring() {
            return gpsMonitoring;
        }

        public void setGpsMonitoring(String gpsMonitoring) {
            this.gpsMonitoring = gpsMonitoring;
        }

        public String getFireExtinguisher() {
            return fireExtinguisher;
        }

        public void setFireExtinguisher(String fireExtinguisher) {
            this.fireExtinguisher = fireExtinguisher;
        }

        public String getEscapeDoor() {
            return escapeDoor;
        }

        public void setEscapeDoor(String escapeDoor) {
            this.escapeDoor = escapeDoor;
        }

        public String getSafetyHammer() {
            return safetyHammer;
        }

        public void setSafetyHammer(String safetyHammer) {
            this.safetyHammer = safetyHammer;
        }

        public String getOillOilLevel() {
            return oillOilLevel;
        }

        public void setOillOilLevel(String oillOilLevel) {
            this.oillOilLevel = oillOilLevel;
        }

        public String getAmountOfAntifreeze() {
            return amountOfAntifreeze;
        }

        public void setAmountOfAntifreeze(String amountOfAntifreeze) {
            this.amountOfAntifreeze = amountOfAntifreeze;
        }

        public String getBakeFluid() {
            return bakeFluid;
        }

        public void setBakeFluid(String bakeFluid) {
            this.bakeFluid = bakeFluid;
        }

        public String getBeltTightness() {
            return beltTightness;
        }

        public void setBeltTightness(String beltTightness) {
            this.beltTightness = beltTightness;
        }

        public String getTirePressureScrews() {
            return tirePressureScrews;
        }

        public void setTirePressureScrews(String tirePressureScrews) {
            this.tirePressureScrews = tirePressureScrews;
        }

        public String getLights() {
            return lights;
        }

        public void setLights(String lights) {
            this.lights = lights;
        }

        public String getGuideBoard() {
            return guideBoard;
        }

        public void setGuideBoard(String guideBoard) {
            this.guideBoard = guideBoard;
        }

        public String getClutch() {
            return clutch;
        }

        public void setClutch(String clutch) {
            this.clutch = clutch;
        }

        public String getBrake() {
            return brake;
        }

        public void setBrake(String brake) {
            this.brake = brake;
        }

        public String getSteeringWheel() {
            return steeringWheel;
        }

        public void setSteeringWheel(String steeringWheel) {
            this.steeringWheel = steeringWheel;
        }

        public String getInstrumentPanel() {
            return instrumentPanel;
        }

        public void setInstrumentPanel(String instrumentPanel) {
            this.instrumentPanel = instrumentPanel;
        }

        public String getBusNumber() {
            return busNumber;
        }

        public void setBusNumber(String busNumber) {
            this.busNumber = busNumber;
        }

    @Override
    public String toString() {
        return "BusInfo{" +
                "id=" + id +
                ", busId=" + busId +
                ", busNumber='" + busNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", createTime=" + createTime +
                ", engineHygiene='" + engineHygiene + '\'' +
                ", airFilter='" + airFilter + '\'' +
                ", batteryHealth='" + batteryHealth + '\'' +
                ", medicineBox='" + medicineBox + '\'' +
                ", gpsMonitoring='" + gpsMonitoring + '\'' +
                ", fireExtinguisher='" + fireExtinguisher + '\'' +
                ", escapeDoor='" + escapeDoor + '\'' +
                ", safetyHammer='" + safetyHammer + '\'' +
                ", oillOilLevel='" + oillOilLevel + '\'' +
                ", amountOfAntifreeze='" + amountOfAntifreeze + '\'' +
                ", bakeFluid='" + bakeFluid + '\'' +
                ", beltTightness='" + beltTightness + '\'' +
                ", tirePressureScrews='" + tirePressureScrews + '\'' +
                ", lights='" + lights + '\'' +
                ", guideBoard='" + guideBoard + '\'' +
                ", clutch='" + clutch + '\'' +
                ", brake='" + brake + '\'' +
                ", steeringWheel='" + steeringWheel + '\'' +
                ", instrumentPanel='" + instrumentPanel + '\'' +
                '}';
    }
}
