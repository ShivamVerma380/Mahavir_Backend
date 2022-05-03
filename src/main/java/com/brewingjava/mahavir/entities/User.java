package com.brewingjava.mahavir.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Generated;

@Document(collection = "users")
@Component
public class User {

    @Id
    private String email;

    private String name;

    private String address;
    
    private String phoneNo;
    
    private String city;
    
    private String state;
    
    private String pinCode;
    
    public User() {
    }

    public User(String email, String name, String address, String phoneNo, String city, String state, String pinCode) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.city = city;
        this.state = state;
        this.pinCode = pinCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return "User [address=" + address + ", city=" + city + ", email=" + email + ", name=" + name + ", phoneNo=" + phoneNo
                + ", pinCode=" + pinCode + ", state=" + state + "]";
    }

    

    


    



}
