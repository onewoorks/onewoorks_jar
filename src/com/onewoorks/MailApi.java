package com.onewoorks;

/**
 *
 * @author iwang
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

/**
 *
 * @author lydiairwan
 */
public class MailApi {

    public String sender;
    public String to;
    public String subject;
    public String message;

    public static void main(String args[]) throws Exception {

    }

    public void postEmail() {
        JSONObject json = new JSONObject();

        json.put("sender", this.sender);
        json.put("to", this.to);
        json.put("subject",this.subject);
        json.put("message", this.message);

        String emailData = json.toString();

        try {
            String postUri = "http://onewoorks-solutions.com/api/mail/";
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(postUri);

            StringEntity input = new StringEntity(emailData);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = client.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            client.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void postMpcSmtp() {
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
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
   }

}
