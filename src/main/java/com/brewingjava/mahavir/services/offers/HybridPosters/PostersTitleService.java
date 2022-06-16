package com.brewingjava.mahavir.services.offers.HybridPosters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.brewingjava.mahavir.daos.admin.AdminDao;
import com.brewingjava.mahavir.daos.offers.HybridPosters.PostersTitleDao;
import com.brewingjava.mahavir.entities.admin.Admin;
import com.brewingjava.mahavir.entities.offers.HybridPosters.ImageOffer;
import com.brewingjava.mahavir.entities.offers.HybridPosters.PostersTitle;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

@Repository
public class PostersTitleService {

    @Autowired
    public ResponseMessage responseMessage;

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public AdminDao adminDao;

    @Autowired
    public Admin admin;

    @Autowired
    public PostersTitleDao postersTitleDao;

    public ResponseEntity<?> addImagePosters(String auth,String title ,MultipartFile image,ArrayList<String> modelNos){
        try {
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can add product posters");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
            }
            
            PostersTitle postersTitle = postersTitleDao.getPostersTitleBytitle(title);
            if(postersTitle==null){
                PostersTitle postersTitle2 = new PostersTitle();
                postersTitle2.setModelNos(new HashSet<>());
                postersTitle2.setTitle(title);
                ImageOffer imageOffer = new ImageOffer();
                imageOffer.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
                imageOffer.setModelNos(modelNos);
                ArrayList<ImageOffer> list = new ArrayList<>();
                list.add(imageOffer);
                postersTitle2.setImageModelNos(list);
                postersTitleDao.save(postersTitle2);
                
            }else{
                ImageOffer imageOffer = new ImageOffer();
                imageOffer.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
                imageOffer.setModelNos(modelNos);
                ArrayList<ImageOffer> list = postersTitle.getImageModelNos();
                list.add(imageOffer);
                postersTitle.setImageModelNos(list);
                postersTitleDao.save(postersTitle);
            }
            responseMessage.setMessage("Product Poster image added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
            

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }

    public ResponseEntity<?> addNonImagePosters(String auth,String title ,ArrayList<String> modelNos){
        try {
            String token = auth.substring(7);
            String email = jwtUtil.extractUsername(token);
            admin = adminDao.findByEmail(email);
            if(admin==null){
                responseMessage.setMessage("Only admin can add posters");
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseMessage);
            }
            PostersTitle postersTitle = new PostersTitle();
            postersTitle.setTitle(title);
            
            postersTitle.setModelNos(new HashSet<>(modelNos));
            postersTitle.setImageModelNos(new ArrayList<>());
            postersTitleDao.save(postersTitle);
            responseMessage.setMessage("Product Posters added successfully");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }


    public ResponseEntity<?> getPosters(){
        try {
            return ResponseEntity.ok(postersTitleDao.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
