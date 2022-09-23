package com.brewingjava.mahavir.services.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.product.FilterCriteriasDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.product.FilterCriterias;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Repository
@Component
public class FilterCriteriasService {
    
    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public Admin admin;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public FilterCriteriasDao filterCriteriasDao;

    public ResponseEntity<?> addFilterCriteries(String auth,String category,HashMap<String, ArrayList<String>> filterCriterias) {
        try {
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if (admin != null) {
                FilterCriterias obj = new FilterCriterias(category, filterCriterias);
                filterCriteriasDao.save(obj);
                responseMessage.setMessage("Filter Criterias added successfully");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            } else {
                responseMessage.setMessage("You are not authorized to perform this action");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }

    }

    public ResponseEntity<?> getFilterCriterias(String category){
        try {
            FilterCriterias filterCriterias = filterCriteriasDao.getFilterCriteriasBycategory(category);
            if(filterCriterias==null){
                responseMessage.setMessage("Filter criterias not found");
                return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            }
            responseMessage.setMessage("Filter Criterias fetched successfully");
            return ResponseEntity.status(HttpStatus.OK).body(filterCriterias);
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

}
