package com.brewingjava.mahavir.entities.user;

import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;




@Document(collection = "users")
@Component
public class UserRequest {

    @Id
    private String email;

    private String password;

    private String firstName;

    private String lastName;
    
    private String phoneNo;
    
    private List<Orders> productsBoughtByUser; 
    
    private HashSet<String> userCartProducts;


    
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


    public List<Orders> getProductsBoughtByUser() {
        return productsBoughtByUser;
    }
    


    public void setProductsBoughtByUser(List<Orders> productsBoughtByUser) {
        this.productsBoughtByUser = productsBoughtByUser;
    }


    
    public HashSet<String> getUserCartProducts() {
        return userCartProducts;
    }


    public void setUserCartProducts(HashSet<String> userCartProducts) {
        this.userCartProducts = userCartProducts;
    }


    @Override
    public String toString() {
        return "UserRequest [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", password="
                + password + ", phoneNo=" + phoneNo + ", productsBoughtByUser=" + productsBoughtByUser
                + ", userCartProducts=" + userCartProducts + "]";
    }


    

    
    

}