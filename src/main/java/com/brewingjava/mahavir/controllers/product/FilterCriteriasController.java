package com.brewingjava.mahavir.controllers.product;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brewingjava.mahavir.helper.ResponseMessage;
import com.brewingjava.mahavir.services.product.FilterCriteriasService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FilterCriteriasController {

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public FilterCriteriasService filterCriteriasService;
    
    @PostMapping("/filtercriterias/{category}")
    public ResponseEntity<?> addFilterCriterias(@RequestHeader("Authorization") String authorization,@PathVariable("category") String category,@RequestBody HashMap<String, ArrayList<String>> filterCriterias) {
        try {
            return filterCriteriasService.addFilterCriteries(authorization,category,filterCriterias);
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

    @GetMapping("/filtercriterias/{category}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> getFilterCriterias(@PathVariable("category") String category){
        try {
            return filterCriteriasService.getFilterCriterias(category);
        } catch (Exception e) {
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseMessage);
        }
    }

}
