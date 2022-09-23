package com.brewingjava.mahavir.entities.admin;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection="admin")
@Component
public class Admin {
    
    @Id
    private String email;

    private String name;

    private String password;

    private String phoneNo;

    public Admin() {
    }

    public Admin(String email, String name, String password, String phoneNo) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNo = phoneNo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "Admin [email=" + email + ", name=" + name + ", password=" + password + ", phoneNo=" + phoneNo + "]";
    }

    
    
}
