import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String to = "ekanshnishad@gmail.com";

	      String from = "IIT2019182@iiita.ac.in";

	      String host = "localhost";

	      Properties properties = System.getProperties();

	      properties.setProperty("mail.smtp.host", host);

	      Session session = Session.getDefaultInstance(properties);

	      try {
	         MimeMessage message = new MimeMessage(session);

	         message.setFrom(new InternetAddress(from));

	       
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         message.setSubject("This is the Subject Line!");

	         message.setContent("<h1>This is actual message</h1>", "text/html");

	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

}
