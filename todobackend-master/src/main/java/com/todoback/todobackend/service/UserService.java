package com.todoback.todobackend.service;

import com.todoback.todobackend.domain.AuthenticationDTO;
import com.todoback.todobackend.domain.RegisterDTO;
import com.todoback.todobackend.domain.User;
import com.todoback.todobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public void registerUser(RegisterDTO registerDTO) {
        Optional<User> userOptional = userRepository.findByUsername(registerDTO.getLogin());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setUsername(registerDTO.getLogin());
            user.setEmail(registerDTO.getEmail());
            user.setFirstName(registerDTO.getFirstName());
            user.setLastName(registerDTO.getLastName());
            user.setLolUsername(registerDTO.getLolData().getLolUsername());
            user.setLOLServer(registerDTO.getLolData().getLolServer());
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(registerDTO.getPassword()));
            User savedUser = userRepository.save(user);
            System.out.println("Zapisano nowego użytkownika z loginem: " + registerDTO.getLogin());
            try {
                mailService.sendActivationEmail(savedUser);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthenticationDTO authenticateUser(User user) {
        // wyszukujemy uzytkownika w baie
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
        // tworzymy obiekt DRO ktory bedzie odpowiedzia do frontendu
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        // jesli uzytkownik jest dostepny w bazie
        if (userOptional.isPresent()) {
            // jesli uzytkownik jest aktywowany
            if (userOptional.get().isActivated()) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                // jesli haslo uzytkownika jest poprawne
                if (userOptional.get().getUsername().equals(user.getUsername()) && encoder.matches(user.getPassword(), userOptional.get().getPassword())) {
                    authenticationDTO.setSuccess(true);
                } else {
                    authenticationDTO.setSuccess(false);
                }
                // jesli konto uzytkownika nie jest aktywowane
            } else {
                authenticationDTO.setSuccess(false);
                authenticationDTO.setMessage("Konto użytkownika nie jest aktywowane");
            }
            // jesli uzytkownik nie zostal znaleziony w bazie
        } else {
            authenticationDTO.setSuccess(false);
        }

        return authenticationDTO;
    }

    public void activateUser(String activationId) {
        Optional<User> userOptional = userRepository.findByActivationId(activationId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActivated(true);
            userRepository.save(user);
        }
    }

    public int prepareUserIdFromUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get().getId().intValue();
        } else {
            return -1;
        }
    }

    public User getUserDataByUsername(String userName){
        Optional<User> userOptional = userRepository.findByUsername(userName);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null;
        }
    }
}