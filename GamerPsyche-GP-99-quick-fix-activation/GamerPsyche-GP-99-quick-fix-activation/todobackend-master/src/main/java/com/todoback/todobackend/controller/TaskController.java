package com.todoback.todobackend.controller;

import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.R4JFetch;
import com.todoback.todobackend.service.TestScrap;
import com.todoback.todobackend.service.UserService;
import com.todoback.todobackend.service.MailService;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class TaskController {

    @Autowired
    R4JFetch r4jFetch;
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

     @GetMapping("user/getOrianna/{username}/{queue}")
    public void sendOrianna(@PathVariable String username, @PathVariable int queue)
     {
         Optional<User> userOptional = userRepository.findByUsername(username);
         if(userOptional.isPresent()){
             User user = userOptional.get();
             System.out.println(Queue.withId(queue));
             Queue queue1 = Queue.withId(420);
             oriannaFetch.getWinRateByQueue(queue1,user);
         }

     }
    @GetMapping("user/r4/{username}")
    public String testR4(@PathVariable String username)
    {

            Optional<User> userOptional = userRepository.findByUsername(username);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                System.out.println("poszlo");
                r4jFetch.R4JFetchBasicInfo(user);
            }
        return "chuj";
    }


}