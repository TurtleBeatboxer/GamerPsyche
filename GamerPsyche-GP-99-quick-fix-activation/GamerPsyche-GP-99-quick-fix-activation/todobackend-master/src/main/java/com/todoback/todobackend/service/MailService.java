package com.todoback.todobackend.service;

import com.todoback.todobackend.domain.MessageDTO;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
public class MailService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public void sendActivationEmail(User user) throws MessagingException {
        String uuidString = prepareUUID();
        String activationLink = prepareActivationLink(uuidString);
        String codeNumber = prepareUniqueCodeNumber();

        assignValuesToUser(user, uuidString);

        String userName = "origami.projects.mail@gmail.com";
        String password = "DupaDupa123Pizda";
        String alertEmail = userName;

        // NADAWCA
        String recipientAddress = user.getEmail();

        // z dokumentacji GMAIL
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // z dokumentacji JAVAX MAIL
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, password);
                    }
                });

        // Tworzenie wiadomości
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(alertEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));
        message.setSubject("Mail from Origami Project");



        message.setText(prepareMessageContent(activationLink, codeNumber));

        // Wysyłka wiadomości
        Transport.send(message);
        System.out.println("Wyslano wiadomosc do " + recipientAddress);

    }

    public void assignValuesToUser(User user, String activationId){
        user.setActivationId(activationId);
        userRepository.save(user);
    }

    public String prepareUUID(){
        return UUID.randomUUID().toString();
    }

    public String prepareActivationLink(String uuidString) {
        String address = "http://localhost:8080/user/activation/";
        return address + uuidString;
    }

    public String prepareMessageContent(String activationLink, String codeNumber) {
        StringBuilder builder = new StringBuilder();

        builder.append("Dzień dobry ")
                .append("\n")
                .append("Zostałeś zarejestrowany w bazie OrigamiProject")
                .append("\n")
                .append("Kliknij w poniższy link aby aktywować konto: ")
                .append("\n")
                .append(activationLink)
                .append("\n")
                .append("Twój unikalny kod to: ")
                .append(codeNumber)
                .append("\n")
        ;

        return builder.toString();
    }
   public String prepareMessage(String link){
     StringBuilder builder = new StringBuilder();

     builder.append("Dzień dobry")
     .append("\n")
     .append("kliknij ten link aby zmienic hasło")
     .append("\n")
     .append(link);

     return builder.toString();
   }
   public String prepareChangeString(String uuidString){
       String str = "http://localhost:8080/user/changePassword/";
       return str + uuidString;
   }
    public String prepareUniqueCodeNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000);
    }

    public MessageDTO sendChangeEmail(String email) throws MessagingException{
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            String userName = "origami.projects.mail@gmail.com";
            String password = "DupaDupa123Pizda";
            String alertEmail = userName;

            // NADAWCA
            String recipientAddress = email;

            // z dokumentacji GMAIL
            Properties properties = new Properties();
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties,
                    new javax.mail.Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(alertEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddress));
            message.setSubject("Mail from Origami Project");

            String uuidString = prepareUUID();
            String activationLink = prepareChangeString(uuidString);
            User user = userOptional.get();
            user.setPasswordChangeId(uuidString);
            userRepository.save(user);
            message.setText(prepareMessage(activationLink));

            // Wysyłka wiadomości
            Transport.send(message);
            System.out.println("Wyslano wiadomosc do " + recipientAddress);
            MessageDTO mess = new MessageDTO();
            mess.setSuccess(true);
            mess.setMessage("ok");
            return mess;
        } else {
            return null;
        }


    }

}
//