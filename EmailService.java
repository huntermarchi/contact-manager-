package com.example.demo.Project;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import org.springframework.stereotype.Service;
@Service
public class EmailService 
{
	 private String from="";//Enter Your Email
	 private String pass="";//Enter Your Email Password
	 private String sub="Verification from SCM:Reset your Password";
     public boolean sendMail(String to,int otp) throws MessagingException
     {
    	 Properties p=new Properties();
    	 p.put("mail.smtp.host","smtp.gmail.com");
    	 p.put("mail.smtp.port", "587");
    	 p.put("mail.smtp.auth", "true");
    	 p.put("mail.smtp.starttls.enable", true);
    	 Session s=Session.getInstance(p, new Authenticator() {
    		 protected PasswordAuthentication getPasswordAuthentication() 
    		 {
    				return new PasswordAuthentication(from,pass);
    			    }
    	 });
         MimeMessage m=new MimeMessage(s);
         m.setFrom(from);
         m.addRecipient(RecipientType.TO, new InternetAddress(to));
         m.setSubject(sub);
         m.setText("Do Not Share OTP to Anyone");
         m.setContent("<h3>Your OTP is:"+otp+"</h3>", "text/html");
         Transport.send(m);
         return true;
     }
}
