package com.todoback.todobackend.controller;

import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.TestScrap;
import com.todoback.todobackend.service.impl.TestScrapImpl;
import com.todoback.todobackend.service.UserService;
import com.todoback.todobackend.service.MailService;
import com.todoback.todobackend.service.orianna.OriannaFetch;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    TestScrap testScrap;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OriannaFetch oriannaFetch;

    @PostMapping("/user/authenticate")
    public AuthenticationDTO authenticateUser(@RequestBody User user) {
        return userService.authenticateUser(user);
    }

    @PostMapping("/user/register")
    public void registerUser(@RequestBody RegisterDTO registerDTO) {
        userService.registerUser(registerDTO);
    }

    @GetMapping("/user/activation/{activationId}")
    public RedirectView activateUser(@PathVariable String activationId) {
        userService.activateUser(activationId);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:4200/activated");
        return redirectView;
    }


    @GetMapping("user/changePassword/{changeId}")
    public RedirectView changePass(@PathVariable String changeId) {
        RedirectView redirectView = new RedirectView();

        redirectView.setUrl("http://localhost:4200/changePassword/" + changeId);
        return redirectView;
    }

    @GetMapping("/user/id/{username}")
    public int prepareUserId(@PathVariable String username) {
        return userService.prepareUserIdFromUsername(username);
    }

    @GetMapping("/user/{username}")
    public User getUserData(@PathVariable String username) {
        return userService.getUserDataByUsername(username);
    }

    //template url
    @PostMapping("/user/change-password")
    public MessageDTO changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.validateChangePasswordDTO(changePasswordDTO);
    }

    @PostMapping("/user/email/changePassword/{changeId}")
    public MessageDTO changePassEmail(@PathVariable String changeId, @RequestBody String password) {
        return userService.changePassword(changeId, password);
    }

    @PostMapping("/user/changeReq")
    public MessageDTO changeEmail(@RequestBody String email) {
        try {
            return mailService.sendChangeEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    @GetMapping("user/getMainData/{username}")
    public MainUserDTO sendMainData(@PathVariable String username) {
        return userService.sendMainData(username);
    }

     @GetMapping("user/LOLUserDATA/{lolServer}/{lolUsername}")
     public LOLUserDATA getLOLUserDATA(@PathVariable String lolServer, @PathVariable String lolUsername){
          return  testScrap.getLOLUserDATA(lolServer, lolUsername);
     }

     @GetMapping("user/getOrianna/{username}")
    public String sendOrianna(@PathVariable String username)
     {
         Optional<User> userOptional = userRepository.findByUsername(username);
         if(userOptional.isPresent()){
             User user = userOptional.get();
             return oriannaFetch.fetchBasicInfo(user);
         }
         return "chuj";
     }


}