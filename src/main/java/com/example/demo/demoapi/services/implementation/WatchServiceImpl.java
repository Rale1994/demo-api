package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.WatchRequest;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.entity.Watch;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.repositories.WatchRepository;
import com.example.demo.demoapi.services.WatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class WatchServiceImpl implements WatchService {

    private final WatchRepository watchRepository;
    private final UserRepository userRepository;

    @Override
    public void addWatch(WatchRequest watchRequest) {
        Optional<User> userOptional = userRepository.findById(watchRequest.getUser());
        if (userOptional.isEmpty()) {
            throw new ApiRequestException("User does not exist!");
        }
        User user = userOptional.get();
        Watch watch = new Watch(watchRequest);
        watch.setUser(user);
        watchRepository.save(watch);
    }
}
