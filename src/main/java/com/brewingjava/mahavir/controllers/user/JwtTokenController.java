package com.brewingjava.mahavir.controllers.user;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brewingjava.mahavir.helper.JwtResponse;
import com.brewingjava.mahavir.helper.JwtUtil;
import com.brewingjava.mahavir.helper.ResponseMessage;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
@CrossOrigin(origins = "*")
public class JwtTokenController {

    @Autowired
    public JwtUtil jwtUtil;

    @Autowired
    public JwtResponse jwtResponse;

    @Autowired
    public ResponseMessage responseMessage;

    @GetMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception{
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String,Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);

        String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString()); 

        jwtResponse.setMessage("Token refreshed successfully!!");
        jwtResponse.setToken(token);
        return ResponseEntity.ok(jwtResponse);


    }

    @GetMapping("/welcome")
    public ResponseEntity<?> getWelcome(){
        try {
            responseMessage.setMessage("Welcome");
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);

        } catch (Exception e) {
            e.printStackTrace();
            responseMessage.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
    

    private Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
    }

}
