package com.todoback.todobackend.controller;

import com.google.gson.Gson;
import com.merakianalytics.datapipelines.sources.Get;
import com.todoback.todobackend.domain.*;
import com.todoback.todobackend.domain.Action;
import com.todoback.todobackend.repository.UserRepository;
import com.todoback.todobackend.service.LOL.AIDataService;
import com.todoback.todobackend.service.LOL.HelperService;
import com.todoback.todobackend.service.LOL.R4JFetch;
//import com.todoback.todobackend.service.TestScrap;
import com.todoback.todobackend.service.UserService;
import com.todoback.todobackend.service.MailService;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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
    @Autowired
    private AIDataService aiDataService;

    @GetMapping("/AI/data/winRate/byChampion")
    public void test(){
        aiDataService.getWinRateByRole("koczokok", LeagueShard.EUN1);
    }
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
        return r4jFetch.getMostPlayedChampions(username);

    }

    @PostMapping("AI")
    public void getAIData(@RequestBody AIReqBody body) throws Exception {
        System.out.println("Controller");
        aiDataService.userSearchBrute(body.getUsername(), body.getChampionId());
       // aiDataService.userSearch(username, championId);
    }


    @GetMapping("user/{username}/getMatchHistory")
    public List<MatchHistoryDTO> getMatchHistory(@PathVariable String username){
       return r4jFetch.getMatchHistory(username);
    }

    @PostMapping("/get/data/champion-select/by/action")
    public void getMatchDataByChampion(@RequestBody String body) throws Exception {
         r4jFetch.getDataFromUserMatch(body);
    }

}