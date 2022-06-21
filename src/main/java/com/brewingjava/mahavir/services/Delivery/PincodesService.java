package com.brewingjava.mahavir.services.Delivery;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.brewingjava.mahavir.daos.Delivery.PincodesDao;
import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.entities.Delivery.Pincodes;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Component
@Service
public class PincodesService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public PincodesDao pincodesDao;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    public ResponseEntity<?> addPincodes(String authorization,ArrayList<Integer> pincodes){
        try {
            String token = authorization.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can add pincodes");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            for(int i=0;i<pincodes.size();i++){
                Pincodes obj = new Pincodes(pincodes.get(i));
                pincodesDao.save(obj);
            }
            responseMessage.setMessage("Pincodes saved successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            
        }

    }

    public ResponseEntity<?> getPincodes(){
        try {
            List<Pincodes> pincodes = pincodesDao.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(pincodes);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

}
