package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.UserDetailsRequest;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDTO create(UserDetailsRequest userDetailsRequest);

    Page<UserResponseDTO> getAll(Pageable pageable, Predicate predicate);

    UserResponseDTO getOne(long id);

    UserResponseDTO update(long id, UserDetailsRequest userDetailsRequest) throws Exception;

    boolean delete(long userId);
}
