package com.todoback.todobackend.controller;

import com.google.gson.Gson;
import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.domain.Action;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.HelperService;
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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
public class TaskController {
    final Gson gson = new Gson();
    @Autowired
    private R4JFetch r4jFetch;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OriannaFetch oriannaFetch;
    @Autowired
    private HelperService HelperService;



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

    @GetMapping("user/getOrianna/{username}/{queue}")
    public float fetchWinRateByQueue(@PathVariable String username, @PathVariable int queue) {
            return HelperService.fetchWinRateByQueue(username, queue);
    }

    @GetMapping("user/{username}/getMostPlayedChampions")
    public  Map<String, Integer> getMostPlayedChampions(@PathVariable String username){
//        Map<String, Integer> data = new HashMap<>();
//        Optional<User> userOptional = userRepository.findByUsername(username);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            return r4jFetch.fetchMostPlayedChampions(user);
//        }
//        return data;
        return r4jFetch.getMostPlayedChampions(username);

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
    public void getMatchDataByChampion(@RequestBody String body) throws Exception {
        Action object = gson.fromJson(body, Action.class);
        Optional<User> userOptional = userRepository.findByLolUsername(object.getSummonerName());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(object.getChampionId() > 0){
                r4jFetch.getDataFromUserMatch(object.getChampionId(), object.getSummonerName(), user.getLeagueShard());
            }
        }
    }
    //@PostConstruct()
    public void test(){
        r4jFetch.test();
    }
}