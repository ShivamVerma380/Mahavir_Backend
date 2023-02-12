package com.brewingjava.mahavir.helper.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.brewingjava.mahavir.daos.product.ProductDetailsDao;
import com.brewingjava.mahavir.entities.orders.OrderDetails;
import com.brewingjava.mahavir.entities.product.ProductDetail;
import com.brewingjava.mahavir.helper.ResponseMessage;


@Component
public class EmailOrder {


    @Autowired
    public ProductDetailsDao productDetailsDao;

    @Autowired private JavaMailSender javaMailSender;
    
    

    public void sendEmailToAdmin(String email,OrderDetails orderDetails){
        try {
           
            String to = email;
            String from = "edata@mahavirelectronics.net";
            String subject = "Mahavir Electronics-Pending Order";
            
            String host = "smtpout.secureserver.net";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", "465"); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication

            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, "edata@1234");
                }

            });

            session.setDebug(true);

            //Step 2 Use Mimemessage class to send mail

            MimeMessage m = new MimeMessage(session);

            m.setFrom(from);

            m.addRecipient(RecipientType.TO, new InternetAddress(to));

            m.setSubject(subject);

           
            // Map<String,Integer> map = new HashMap<>();
            ArrayList<OrderResponse> list = new ArrayList<>();
            for(Map.Entry<String,Integer> mp: orderDetails.getProducts().entrySet()){
                String modelNumber = mp.getKey();
                ProductDetail productDetail = productDetailsDao.findProductDetailBymodelNumber(modelNumber);
                // map.put(productDetail.getProductName(),mp.getValue());
                OrderResponse orderResponse = new OrderResponse();
                orderResponse.setProductId(productDetail.getProductId());
                orderResponse.setProductName(productDetail.getProductName());
                orderResponse.setQuantity(mp.getValue());
                list.add(orderResponse);
            }

            String message = "Dear Sushil,\n"+"Deliver The Following order:\n"+"Buy Date:"+orderDetails.getBuyDate()+"\nBuyer Email: "+orderDetails.getBuyerEmail()+"\nPayment Amount:Rs "+orderDetails.getPaymentAmount()+"\nPayment Mode:"+orderDetails.getPaymentMode()+"\nUser Address:"+orderDetails.getUserAddress().getAddress()+","+orderDetails.getUserAddress().getCity()+","+orderDetails.getUserAddress().getState()+","+orderDetails.getUserAddress().getPincode()+
            "\nOrder To Deliver:\n"+list+"\nThanks & Regards,\nMahavir Electronics";

            m.setText(message);

            //Step 3: Transport mail
            Transport.send(m);  //message send karne ke liye
        } catch (Exception e) {
            System.out.println("Error in sendEmailToAdmin");
            e.printStackTrace();
        }
    }

    public void sendEmailToUser(String to,String name,OrderDetails orderDetails){
        // Creating a Simple Mail Message object
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom("edatamahavir@gmail.com");
        mailMessage.setTo(to);
        mailMessage.setText("Dear "+name+",\n"+"Thank you for shopping with Mahavir Electronics.\n"+"\nYour order id is #MEF100"+orderDetails.getOrderId()+"\nThanks & Regards,\nMahavir Electronics");
        mailMessage.setSubject("Mahavir Electronics-Order Purchased");

        // Sending the mail
        javaMailSender.send(mailMessage);
    }

}