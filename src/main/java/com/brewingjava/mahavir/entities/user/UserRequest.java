package com.brewingjava.mahavir.entities.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.orders.OrderDetails;




@Document(collection = "users")
@Component
public class UserRequest {

    @Id
    private String email;

    private String password;

    private String firstName;

    private String lastName;
    
    private String phoneNo;
    
    private List<OrderDetails> productsBoughtByUser; 

    private List<OrderDetails> productsCancelledByUser;

    private List<OrderDetails> productsReturnedByUser;
    
    private HashSet<String> userCartProducts;

    private String token;

    private List<String> addToCompare;  //String of model Numbers

    private ArrayList<UserAddress> userAdresses;

    private ArrayList<String> userWishList;


    
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


    

    
    public HashSet<String> getUserCartProducts() {
        return userCartProducts;
    }


    public void setUserCartProducts(HashSet<String> userCartProducts) {
        this.userCartProducts = userCartProducts;
    }

    




    public List<String> getAddToCompare() {
        return addToCompare;
    }


    public void setAddToCompare(List<String> addToCompare) {
        this.addToCompare = addToCompare;
    }


    

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }


    public ArrayList<UserAddress> getUserAdresses() {
        return userAdresses;
    }


    public void setUserAdresses(ArrayList<UserAddress> userAdresses) {
        this.userAdresses = userAdresses;
    }



    public ArrayList<String> getUserWishList() {
        return userWishList;
    }


    public void setUserWishList(ArrayList<String> userWishList) {
        this.userWishList = userWishList;
    }


    

    public List<OrderDetails> getProductsCancelledByUser() {
        return productsCancelledByUser;
    }


    public void setProductsCancelledByUser(List<OrderDetails> productsCancelledByUser) {
        this.productsCancelledByUser = productsCancelledByUser;
    }


    public List<OrderDetails> getProductsReturnedByUser() {
        return productsReturnedByUser;
    }


    public void setProductsReturnedByUser(List<OrderDetails> productsReturnedByUser) {
        this.productsReturnedByUser = productsReturnedByUser;
    }

    public List<OrderDetails> getProductsBoughtByUser() {
        return productsBoughtByUser;
    }


    public void setProductsBoughtByUser(List<OrderDetails> productsBoughtByUser) {
        this.productsBoughtByUser = productsBoughtByUser;
    }


    @Override
    public String toString() {
        return "UserRequest [addToCompare=" + addToCompare + ", email=" + email + ", firstName=" + firstName
                + ", lastName=" + lastName + ", password=" + password + ", phoneNo=" + phoneNo
                + ", productsBoughtByUser=" + productsBoughtByUser + ", productsCancelledByUser="
                + productsCancelledByUser + ", productsReturnedByUser=" + productsReturnedByUser + ", token=" + token
                + ", userAdresses=" + userAdresses + ", userCartProducts=" + userCartProducts + ", userWishList="
                + userWishList + "]";
    }

    

}
