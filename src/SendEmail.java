import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {

	public static void sendEmail(boolean acStatus, double acTemp, boolean heaterStatus, double heaterTemp, int exhaustSpeed, int fanSpeed,
			double temp, double humid, int aqi, String aqiWarning) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("Preparing to email");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String account = "IIT2019182@iiita.ac.in";
		String password = "dynamo@123";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, password);
				
			}
			
		
		});
		
		String recipent = "ekanshnishad@gmail.com";
		Message message = prepareMessage(session, account, recipent, acStatus, acTemp, heaterStatus, heaterTemp, exhaustSpeed, fanSpeed
				, temp, humid, aqi, aqiWarning) ;
				
		Transport.send(message);
		System.out.println("Message Sent Successfully!!");
	}

	private static Message prepareMessage(Session session, String account, String recipent, boolean acStatus, double acTemp,
			boolean heaterStatus, double heaterTemp, int exhaustSpeed, int fanSpeed, double temp, double humid, int aqi, String aqiWarning) {
		// TODO Auto-generated method stub
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(account));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipent));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019121@iiita.ac.in"));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019140@iiita.ac.in"));
			//message.setRecipient(Message.RecipientType.CC, new InternetAddress("IIT2019181@iiita.ac.in"));
			message.setSubject("Regarding actions taken by HVAC System");
			String acstat = "OFF";
			String heatstat = "OFF";
			String actemperature = " ";
			String heatertemperature = " ";
			String exhaust;
			String fan;
			if(acStatus)
			{	acstat = "ON";
				actemperature = Double.toString(acTemp);
			}
			if(heaterStatus)
			{
				heatstat = "ON";
				heatertemperature = Double.toString(heaterTemp);
			}
			exhaust = Integer.toString(exhaustSpeed);
			fan = Integer.toString(fanSpeed);
			String htmlMessage = "Dear facultys, staff and students,<br>"
					+ "Corresponding Changes have been applied to HVAC System<br><br>"
					+ "AC : " + acstat + "<br>AC temp : " + actemperature + "<br>Heater : " + heatstat + "<br>Heater temp : "
					+ heatertemperature + "<br>Exhaust Speed : " + exhaust + "<br>Fan Speed: " + fan + "<br><br>Current temp : "
					+ temp + "<br>Current humidity : " + humid + "<br>Current AQI : " + aqi + "<br><br>"
							+ "<p style=\"color:Tomato\" >Note : <br>" + aqiWarning + "</p>";
			
			
			
			message.setContent(htmlMessage , "text/html");
			return message;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
