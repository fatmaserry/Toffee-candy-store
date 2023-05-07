package App;

import java.util.Properties;
import java.util.Random;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class OTPConfirmationEmail {
    public String OTPGenerator() {
        Random random = new Random();
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public Boolean sendOTP(String curr_user, String email, String otp){

        boolean flag = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "smtp.gmail.com");

        // Sender's email and password
        String username = "fatmaserry22";
        String password = "pmzdyslcksahfaza";

        //session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setFrom(new InternetAddress("fatmaserry22@gmail.com"));
            message.setSubject("Welcome to Toffee Store! Confirm Your Account with OTP");
            String msg = "Dear " + curr_user + "," +
                    "\n" +
                    "We're excited to welcome you to Toffee Store " +
                    "and thank you for registering with us! To complete your account registration, " +
                    "Please use the following OTP to verify your account: " + otp +
                    "\n" +
                    "Thank you for choosing Toffee Store. We look forward to providing you with the best possible shopping experience.\n" +
                    "\n" +
                    "Sincerely,\n" + "Toffee Store Team";
            message.setText(msg);
            Transport.send(message);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}