package com.brewingjava.mahavir.entities.user;

import org.springframework.stereotype.Component;

@Component
public class UserAddress {
    
    private String name;

    private String mobileNumber;

    private String pincode;

    private String locality;

    private String address;

    private String city;

    private String state;

    private String landmark;  //optional

    private String alternateMobile; //optional

    private String addressType; //work(10am-5pm delivery) ; home(all day delivery)

    public UserAddress() {
    }

    public UserAddress(String name, String mobileNumber, String pincode, String locality, String address, String city,
            String state, String landmark, String alternateMobile, String addressType) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.pincode = pincode;
        this.locality = locality;
        this.address = address;
        this.city = city;
        this.state = state;
        this.landmark = landmark;
        this.alternateMobile = alternateMobile;
        this.addressType = addressType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return "UserAddress [address=" + address + ", addressType=" + addressType + ", alternateMobile="
                + alternateMobile + ", city=" + city + ", landmark=" + landmark + ", locality=" + locality
                + ", mobileNumber=" + mobileNumber + ", name=" + name + ", pincode=" + pincode + ", state=" + state
                + "]";
    }


    
}
