package com.brewingjava.mahavir.helper;

import org.springframework.stereotype.Component;

@Component
public class OtpResponse {
    
    public String message,otp;

    public OtpResponse() {
    }

    public OtpResponse(String message, String otp) {
        this.message = message;
        this.otp = otp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "OtpResponse [message=" + message + ", otp=" + otp + "]";
    }

    
}
