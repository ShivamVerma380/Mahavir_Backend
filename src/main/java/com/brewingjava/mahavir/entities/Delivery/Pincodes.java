package com.brewingjava.mahavir.entities.Delivery;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "pincodes")
public class Pincodes {
    
    @Id
    private int pincode;

    public Pincodes() {
    }

    public Pincodes(int pincode) {
        this.pincode = pincode;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return "Pincodes [pincode=" + pincode + "]";
    }

    
    

}
