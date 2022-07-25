package com.todoback.todobackend.controller;

import com.google.gson.Gson;
import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.domain.Action;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.R4JFetch;
//import com.todoback.todobackend.service.TestScrap;
import com.todoback.todobackend.service.UserService;
import com.todoback.todobackend.service.MailService;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.impl.lol.raw.SummonerAPI;
import no.stelar7.api.r4j.pojo.lol.summoner.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
public class TaskController {
    final Gson gson = new Gson();
    @Autowired
    R4JFetch r4jFetch;
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
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

    // template url
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
    public void getLOLUserDATA(@PathVariable String lolServer, @PathVariable String lolUsername) {
        // return testScrap.getLOLUserDATA(lolServer, lolUsername);
    }

    @GetMapping("user/getOrianna/{username}/{queue}")
    public float fetchWinRateByQueue(@PathVariable String username, @PathVariable int queue) {

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Queue queueOrianna = Queue.withId(queue);
            if(queue == 400){
                Optional<GameQueueType> queueOptional = GameQueueType.getFromId(queue);
                if (queueOptional.isPresent()) {
                    GameQueueType queueR4J= queueOptional.get();
                    float data = r4jFetch.R4JFetchWinRateByQueue(user, queueR4J);
                    System.out.println("r4j");
                    return data;
                }
            }
            System.out.println("Ranked");
            try {
                float data = oriannaFetch.getWinRateByQueue(queueOrianna, user);
                return data;
            } catch (Exception e) {
                if (e.getMessage() == "data null") {
                    Optional<GameQueueType> queueOptional = GameQueueType.getFromId(queue);
                    if (queueOptional.isPresent()) {
                        GameQueueType queueR4J= queueOptional.get();
                        float data = r4jFetch.R4JFetchWinRateByQueue(user, queueR4J);
                        System.out.println("r4j");
                        return data;
                    }
                }
            }
        }
        return -1;
    }

    @GetMapping("user/{username}/getMostPlayedChampions")
    public  Map<String, Integer> getMostPlayedChampions(@PathVariable String username){
        Map<String, Integer> data = new HashMap<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
             return r4jFetch.getMostPlayedChampions(user);
        }
      return data;

    }

    @GetMapping("user/{username}/getMatchHistory")
    public List<MatchHistoryDTO> getMatchHistory(@PathVariable String username){
        List<MatchHistoryDTO> data = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return r4jFetch.getMatchHistory(user);

        }
        return data;
    }

    @PostMapping("/get/data/champion-select/by/action")
    public void getMatchDataByChampion(@RequestBody String body){
        Action object = gson.fromJson(body, Action.class);
        System.out.println(object);
        System.out.println(body);
        if(object.getChampionId() > 0){
            r4jFetch.getDataFromUserMatch(object.getChampionId());
        }


    }
    @PostConstruct()
    public void test(){
        System.out.println("hello");
        r4jFetch.getDataFromUserMatch(523);
    }
}