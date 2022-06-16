package com.todoback.todobackend.service;

import com.merakianalytics.orianna.Orianna;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.OriannaUsagePreparationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
public class UserService {
    OriannaUsagePreparationService oriannaUsagePreparationService = new OriannaUsagePreparationService();
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
            user.setLolRegion(oriannaUsagePreparationService.translateEnumServerToRiotRegion(registerDTO.getLolData().getLolServer()));
            user.setRiotUserPuuId(oriannaUsagePreparationService.getRiotUserPuuId(user));
            user.setLeagueShard(oriannaUsagePreparationService.translateEnumToLeagueShard(registerDTO.getLolData().getLolServer()));
            System.out.println(Orianna.summonerWithPuuid(user.getRiotUserPuuId()).withRegion(user.getLolRegion()).get().getLevel());
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
            System.out.println("Obecny w bazie");
            // jesli uzytkownik jest aktywowany
            if (userOptional.get().isActivated()) {
                PasswordEncoder encoder = new BCryptPasswordEncoder();
                // jesli haslo uzytkownika jest poprawne
                System.out.println("Aktywny");
                if (userOptional.get().getUsername().equals(user.getUsername()) && encoder.matches(user.getPassword(), userOptional.get().getPassword())) {
                    authenticationDTO.setSuccess(true);
                    System.out.println("hasło git");
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

    public MessageDTO validateChangePasswordDTO (ChangePasswordDTO changePasswordDTO) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<User> userOptional = userRepository.findByUsername(changePasswordDTO.getUsername());
        MessageDTO messDTO = new MessageDTO();
        String nPE = passwordEncoder.encode(changePasswordDTO.getNewPassword());
        ValidatePasswordDTO validatePasswordDTO = new ValidatePasswordDTO();

        if (!userOptional.isPresent()) {
            messDTO.setSuccess(false);
            messDTO.setMessage("nie ma takiego uzytkownika! CHUJ CI W DUPE! KURWA!");
            return messDTO;
        }
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), userOptional.get().getPassword())){
            messDTO.setSuccess(false);
            messDTO.setMessage("złe hasło aktualne do konta!");
            return messDTO;
        }
        validatePasswordDTO.setUsername(changePasswordDTO.getUsername());
        validatePasswordDTO.setPassword(nPE);
        User user = userOptional.get();
        user.setPassword(nPE);
        userRepository.save(user);
        messDTO.setMessage("Zmiana hasla zaszla pomyslnie");
        messDTO.setSuccess(true);
        return messDTO;
    }

    public MessageDTO changePassword(String changeId, String password){
        Optional<User> userOptional = userRepository.findByPasswordChangeId(changeId);

    MessageDTO mess = new MessageDTO();
        if (userOptional.isPresent()){
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = userOptional.get();
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
            System.out.println("jest");
            mess.setSuccess(true);
            mess.setMessage("a");
            return mess;
        }
        mess.setMessage("a");
        mess.setSuccess(false);
    return mess;

    }

    public MainUserDTO sendMainData(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        MainUserDTO userData = new MainUserDTO();
        if(userOptional.isPresent()){
            User user = userOptional.get();
            userData.setEmail(user.getEmail());
            userData.setLolUsername(user.getLOLUsername());
            userData.setFirstName(user.getFirstName());
            userData.setLastName(user.getLastName());
            userData.setUsername(user.getUsername());
            System.out.println(userData + "hello");
            return userData;
        }else {
            return null;
        }

    }
}