package com.brewingjava.mahavir.helper.orders;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.entities.orders.OrderDetails;
import com.brewingjava.mahavir.helper.ResponseMessage;


@Component
public class EmailOrder {

    

    public void sendEmailToAdmin(String email,OrderDetails orderDetails){
        try {
            String from = "shivam380.testing@gmail.com";
            String to = email;
            String subject = "Mahavir Electronics-Pending Order";
            
            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", "465"); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication

            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, "rzlzpbuushaytpmk");
                }

            });

            session.setDebug(true);

            //Step 2 Use Mimemessage class to send mail

            MimeMessage m = new MimeMessage(session);

            m.setFrom(from);

            m.addRecipient(RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);

           


            String message = "Dear Sushil,\n"+"Deliver The Following order:\n"+orderDetails.toString()+"\nThanks & Regards,\nShivam Verma";

            m.setText(message);

            //Step 3: Transport mail
            Transport.send(m);  //message send karne ke liye
        } catch (Exception e) {
            System.out.println("Error in sendEmailToAdmin");
            e.printStackTrace();
        }
    }

    public void sendEmailToUser(String to,String name,OrderDetails orderDetails){
        try {
            String from = "shivam380.testing@gmail.com";
            String subject = "Mahavir Electronics-Order Purchased";
            
            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", "465"); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication

            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, "rzlzpbuushaytpmk");
                }

            });

            session.setDebug(true);

            //Step 2 Use Mimemessage class to send mail

            MimeMessage m = new MimeMessage(session);

            m.setFrom(from);

            m.addRecipient(RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);

           


            String message = "Dear "+name+",\n"+"Thank you for shopping with Mahavir Electronics.\nYour order:\n"+orderDetails.toString()+"\nThanks & Regards,\nSushil Oswal";

            m.setText(message);

            //Step 3: Transport mail
            Transport.send(m);  //message send karne ke liye
        } catch (Exception e) {
            System.out.println("Error in sendEmailToUser");
            e.printStackTrace();
        }
    }

}
