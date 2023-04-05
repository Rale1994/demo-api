package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.WatchRequest;
import com.example.demo.demoapi.services.WatchService;
import com.example.demo.demoapi.shared.Constants;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(Constants.BASE_URL + "/watch")
@Api
public class WatchController {
    private WatchService watchService;

    @Autowired
    public WatchController(WatchService watchService) {
        this.watchService = watchService;
    }

    @PostMapping
    public ResponseEntity<String> addWatch(@RequestBody WatchRequest watchRequest) {
        watchService.addWatch(watchRequest);
        return new ResponseEntity<>("The watch is added", HttpStatus.OK);
    }
}
