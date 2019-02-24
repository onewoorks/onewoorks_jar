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
    
    public void postMpcSmtpX() {
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
    
    public void postMpcSmtp(){
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
              InternetAddress.parse("hello@onewoorks.com"));

	   // Set Subject: header field
	   message.setSubject("Testing Subject With Api");

	   // Send the actual HTML message, as big as you like
              String maimMessage = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<style type='text/css'>\n"
                + "	#imageSize {\n"
                + "		width: 100%;\n"
                + "		height: 3%;\n"
                + "	}\n"
                + "\n"
                + "	#footer {\n"
                + "		background: rgba(226, 226, 226, 1);\n"
                + "		background: -moz-linear-gradient(left, rgba(226, 226, 226, 1) 0%, rgba(219, 219, 219, 1) 50%, rgba(209, 209, 209, 1) 51%, rgba(254, 254, 254, 1) 100%);\n"
                + "		background:\n"
                + "			-webkit-gradient(left top, right top,\n"
                + "			color-stop(0%, rgba(226, 226, 226, 1)),\n"
                + "			color-stop(50%, rgba(219, 219, 219, 1)),\n"
                + "			color-stop(51%, rgba(209, 209, 209, 1)),\n"
                + "			color-stop(100%, rgba(254, 254, 254, 1)));\n"
                + "		background:\n"
                + "			-webkit-linear-gradient(left,\n"
                + "			rgba(226, 226, 226, 1) 0%,\n"
                + "			rgba(219, 219, 219, 1) 50%, rgba(209, 209, 209, 1) 51%,\n"
                + "			rgba(254, 254, 254, 1) 100%);\n"
                + "		background:\n"
                + "			-o-linear-gradient(left, rgba(226, 226, 226, 1) 0%, rgba(219, 219, 219, 1) 50%, rgba(209, 209, 209, 1) 51%, rgba(254, 254, 254, 1) 100%);\n"
                + "		background: -ms-linear-gradient(left, rgba(226, 226, 226, 10%, rgba(219, 219, 219, 1) 50%, rgba(209, 209, 209, 1) 51%,\n"
                + "			rgba(254, 254, 254, 1) 100%);\n"
                + "		background: linear-gradient(to right, rgba(226, 226, 226, 1) 0%, rgba(219, 219, 219, 1) 50%, rgba (209, 209, 209, 1) 51%, rgba(254, 254, 254, 1) 100%);\n"
                + "		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#e2e2e2',\n"
                + "			endColorstr='#fefefe', GradientType=1);\n"
                + "		width: 100%;\n"
                + "		height: 3%;\n"
                + "	}\n"
                + "</style>\n"
                + "\n"
                + "<body>\n"
                + "\n"
                + "	<img src='https://raw.githubusercontent.com/amirulasrafrazali95/stp_v2/master/src/main/webapp/resources/img/mpc-header.png'\n"
                + "		id='imageSize' />\n"
                + "\n"
                + "	<p>\n"
                + "		Assalamuaikum dan Salam Sejahtera,Terdapatpermohonan PEMBATALAN diterima untuk tindakan.\n"
                + "		Sila log masuk <a href='http://localhost:8090/login'>di sini</a> untuk melihat maklumat permohonan\n"
                + "		pembatalan.Sekian, terima kasih.\n"
                + "	</p>\n"
                + "	<p>\n"
                + "		Maklumat Hubungan: <a href='mailto:flight@mpc.gov.my'>flight@mpc.gov.my</a>.\n"
                + "	</p>\n"
                + "	<footer>\n"
                + "		<div id='footer'></div>\n"
                + "	</footer>\n"
                + "</body>\n"
                + "</html>";
           
	   message.setContent(maimMessage,"text/html");

	   // Send message
	   Transport.send(message);

	   System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
	   e.printStackTrace();
	   throw new RuntimeException(e);
      }
    }

}
