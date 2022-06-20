package com.brewingjava.mahavir.services.email;


import com.brewingjava.mahavir.config.MySecurityConfig;
import com.brewingjava.mahavir.helper.OtpResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.PasswordAuthentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

@Component
@Service
public class EmailVerificationService {
    
    @Autowired
    public OtpResponse otpResponse;

    
    @Autowired
    public JavaMailSender javaMailSender;

    

    @Autowired
    public Configuration config;

    public ResponseEntity<?> embedImage(String host, String port,
    final String userName, final String password, String toAddress,
    String subject, String htmlBody,
    Map<String, String> mapInlineImages,String otp) throws AddressException,MessagingException{
        try{
            


            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", port); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication
            properties.put("mail.user",userName);
            properties.put("mail.password",password);
            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    // return new PasswordAuthentication(from, "shivam380");
                    return new PasswordAuthentication(userName, password);
                    
                }

            });

            session.setDebug(true);

            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(userName));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            //creates message-part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(htmlBody,"text/html");

            //creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (mapInlineImages != null && mapInlineImages.size() > 0) {
                Set<String> setImageID = mapInlineImages.keySet();
                 
                for (String contentId : setImageID) {
                    MimeBodyPart imagePart = new MimeBodyPart();
                    imagePart.setHeader("Content-ID", "<" + contentId + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                     
                    String imageFilePath = mapInlineImages.get(contentId);
                    try {
                        imagePart.attachFile(imageFilePath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
     
                    multipart.addBodyPart(imagePart);
                }
            }

            // msg.setContent(multipart);
            Map<String, String> inlineImages = new HashMap<String, String>();
            inlineImages.put("image1", "C:\\Users\\Shivam Verma\\Desktop\\Mahavir\\OTP.png");

            msg.setContent( htmlBody,"text/html");


            Transport.send(msg);

            otpResponse.setMessage("OTP Sent successfully!!");
            otpResponse.setOtp(otp);

            return ResponseEntity.status(HttpStatus.OK).body(otpResponse);


        }catch(Exception e){
            e.printStackTrace();
            otpResponse.setMessage(e.getMessage());
            otpResponse.setOtp(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otpResponse);
        }
    }

    public ResponseEntity<?> getOtp(String email){
        try {
            String host = "smtp.gmail.com";
            String port = "485";
            String mailFrom = "shivam380.testing@gmail.com";
            String password = "xzdiplmftkwjhlqy";

            String mailTo = email;
            String subject = "Mahavir Electronics OTP Verification";

            Random random = new Random();
            int number = random.nextInt(999999);
            String otp = String.format("%06d", number);

            Map<String,Object> model = new HashMap<>();
            model.put("Welcome","Dear "+email+",Welcome To Mahavir Electronics.");
            model.put("OTP", "Here is your OTP:"+otp);

            MimeMessage message = javaMailSender.createMimeMessage();
            try {
                //Set media Type
                MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
                //add attachment
                helper.addAttachment("OTP.png", new ClassPathResource("OTP.png"));

                Template t = config.getTemplate("email-template.ftl");
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
                helper.setTo(mailTo);
                helper.setText(html, true);
                helper.setSubject(subject);
                helper.setFrom(mailFrom);
                javaMailSender.send(message);
                otpResponse.setMessage("Mail sent successfully");
                otpResponse.setOtp(otp);
                return ResponseEntity.status(HttpStatus.OK).body(otpResponse);
            } catch (Exception e) {
                e.printStackTrace();
                otpResponse.setMessage(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otpResponse);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            otpResponse.setMessage(e.getMessage());
            otpResponse.setOtp(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(otpResponse);
        }

    }

}
