package com.example.ProjectLaptopStore.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class Customer_FindTopCustomerInMonthDTO {
    private String userName;
    private String email;
    private String phoneNumber;
    private Date registrationDate;
    private String address;
    private String city;
    private String district;
    private String ward;
    private String streetAddress;
    private BigDecimal totalAmount;

    public Customer_FindTopCustomerInMonthDTO(String userName, String email, String phoneNumber, Date registrationDate, String address, String city, String district, String ward, String streetAddress, BigDecimal totalAmount) {
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.address = address;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.streetAddress = streetAddress;
        this.totalAmount = totalAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
