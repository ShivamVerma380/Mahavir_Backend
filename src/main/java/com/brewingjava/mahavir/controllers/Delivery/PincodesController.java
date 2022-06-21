package com.brewingjava.mahavir.controllers.Delivery;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.Delivery.PincodesService;

@RestController
@CrossOrigin(origins = "*")
public class PincodesController {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public PincodesService pincodesService;

    @PostMapping("/pincodes")
    public ResponseEntity<?> addPincodes(@RequestHeader("Authorization") String authorization,@RequestParam("pincode") ArrayList<Integer> pincodes){
        try {
            return pincodesService.addPincodes(authorization, pincodes); 
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    @GetMapping("/pincodes")
    public ResponseEntity<?> getPincodes(){
        try {
            return pincodesService.getPincodes();
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
