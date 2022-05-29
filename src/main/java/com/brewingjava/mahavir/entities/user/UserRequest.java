package com.brewingjava.mahavir.entities.user;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Generated;


@Document(collection = "users")
@Component
public class UserRequest {

    @Id
    private String email;

    private String password;

    private String firstName;

    private String lastName;
    
    private String phoneNo;
    
    private List<ProductBought> productsBoughtByUser;
    
    public UserRequest() {
    }


    public UserRequest(String email, String password, String firstName, String lastName, String phoneNo) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
    }




    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPhoneNo() {
        return phoneNo;
    }


    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public List<ProductBought> getProductsBoughtByUser() {
        return productsBoughtByUser;
    }


    public void setProductsBoughtByUser(List<ProductBought> productsBoughtByUser) {
        this.productsBoughtByUser = productsBoughtByUser;
    }


    @Override
    public String toString() {
        return "UserRequest [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
                + password + ", phoneNo=" + phoneNo + ", productsBoughtByUser=" + productsBoughtByUser + "]";
    }


    
    

}
