package com.siasun.entities;

/**
 * Created by hliu on 16/9/16.
 */
public class Address {
    private String province;
    private String city;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Address(String province, String city) {
        this.province = province;
        this.city = city;
    }
}
