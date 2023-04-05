package com.example.demo.demoapi.resource;

import com.example.demo.demoapi.dtos.request.UserDetailsRequest;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.services.UserService;
import com.example.demo.demoapi.shared.Constants;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(Constants.BASE_URL + "/users")
@Api
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponseDTO> getAll(Pageable pageable, @QuerydslPredicate(root = User.class) Predicate predicate) {
        return userService.getAll(pageable, predicate);
    }

    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody UserDetailsRequest userDetailsRequest) {
        return userService.create(userDetailsRequest);
    }

    @GetMapping(path = "/{id}")
    public UserResponseDTO getOne(@PathVariable long id) {
        return userService.getOne(id);
    }

    @PutMapping(path = "/{id}")
    public UserResponseDTO update(@PathVariable long id, @RequestBody UserDetailsRequest userDetailsRequest) throws Exception {
        return userService.update(id, userDetailsRequest);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        boolean deletedUser = userService.delete(id);
        if (deletedUser == true) {
            log.info("User with id:{id} has been deleted");
        } else {
            log.error("We have some problem!");

        }
    }
}
