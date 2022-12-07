package com.example.demo.demoapi.services;

import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserDetailsRequestDTO userDetailsRequestDTO);

    Page<UserResponseDTO> getAll(Pageable pageable, Predicate predicate);

    UserResponseDTO getOne(long id);

    UserResponseDTO update(long id, UserDetailsRequestDTO userDetailsRequestDTO) throws Exception;

    boolean delete(long userId);
}
