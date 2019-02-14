/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.onewoorks;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author iwang
 */
public class MpcSmtp {
    
    public String sender;
    public String to;
    public String subject;
    public String message;

    
    public void postEmail() {
      // Recipient's email ID needs to be mentioned.
//      String to = this.to;

      // Sender's email ID needs to be mentioned
      String from = "flight@mpc.gov.my";//change accordingly
      final String username = "flight@mpc.gov.my";//change accordingly
      final String password = "passwordmis";//change accordingly

      // Assuming you are sending email through relay.jangosmtp.net
      String host = "domino01.mpc.gov.my";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "25");

      // Get the Session object.
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         // Create a default MimeMessage object.
         Message message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(this.to));

         // Set Subject: header field
         message.setSubject(this.subject);

         // Now set the actual message
         message.setText(this.message);

         // Send message
         message.setSentDate(new Date());
         message.setContent(message, "text/html");
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
   }
}
