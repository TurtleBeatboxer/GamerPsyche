package com.todoback.todobackend.service.LOL.impl;

import com.merakianalytics.orianna.types.common.Queue;
import com.todoback.todobackend.service.LOL.HelperService;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todoback.todobackend.service.LOL.OriannaFetch;
import com.todoback.todobackend.repository.UserRepository;
import java.util.Optional;
import com.todoback.todobackend.service.LOL.R4JFetch;
import com.todoback.todobackend.domain.User;
@Service
public class HelperServiceImpl implements HelperService {

    @Autowired
    private R4JFetch r4jFetch;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OriannaFetch oriannaFetch;


    public float fetchWinRateByQueue(String username, int queue) {
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
}
