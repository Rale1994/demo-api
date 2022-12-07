package com.example.demo.demoapi.controller;

import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.services.UserService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
@Api
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponseDTO> getAll(Pageable pageable, @QuerydslPredicate(root = User.class) Predicate predicate ) {
        return userService.getAll(pageable, predicate);
    }


    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody UserDetailsRequestDTO userDetailsRequestDTO) {
        return userService.create(userDetailsRequestDTO);

    }

    @GetMapping(path = "/{id}")
    public UserResponseDTO getOne(@PathVariable long id) {
        return userService.getOne(id);
    }

    @PutMapping(path = "/{id}")
    public UserResponseDTO update(@PathVariable long id, @RequestBody UserDetailsRequestDTO userDetailsRequestDTO) throws Exception {
        return userService.update(id, userDetailsRequestDTO);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        boolean deletedUser = userService.delete(id);
        if (deletedUser == true) {
            System.out.println("Users has been deleted, with id: " + id);
        } else {
            System.out.println("There are some problems!");

        }
    }
}
