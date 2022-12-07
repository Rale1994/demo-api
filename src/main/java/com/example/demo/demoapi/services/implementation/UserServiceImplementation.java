package com.example.demo.demoapi.services.implementation;
import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.UserService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImplementation implements UserService {


    UserRepository userRepository;

    ModelMapper modelMapper;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public UserResponseDTO create(UserDetailsRequestDTO userDetailsRequestDTO) {
        User user = modelMapper.map(userDetailsRequestDTO, User.class);

        Optional<User> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isPresent()) {
            throw new ApiRequestException("User with this email already exist!");
        }
        user.setPassword(passwordEncoder.encode(userDetailsRequestDTO.getPassword()));

        User createdUser = userRepository.save(user);
        log.info("User is created with first name: " + userDetailsRequestDTO.getFirstName() + " and last name: " + userDetailsRequestDTO.getLastName());

        UserResponseDTO returnValue = modelMapper.map(createdUser, UserResponseDTO.class);


        return returnValue;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable, Predicate predicate) {
        log.info("Getting all users...");

        Page<User> users = userRepository.findAll(predicate, pageable);
        List<UserResponseDTO>userStream= users.stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(userStream,pageable,users.getTotalElements());
    }

    @Override
    public UserResponseDTO getOne(long id) {

        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            log.error("Trying to get user with id: " + id + " who does not exist!");
            throw new ApiRequestException("User with id: " + id + " does not exist, please enter another id");

        }
        User user = opUser.get();

        UserResponseDTO returnValue = modelMapper.map(user, UserResponseDTO.class);

        log.info("Picked user with id: " + id);
        return returnValue;
    }

    @Override
    public UserResponseDTO update(long id, UserDetailsRequestDTO userDetailsRequestDTO) throws Exception {
        log.info("Updating  user...");
        Optional<User> opUser = userRepository.findById(id);
        if (!opUser.isPresent()) {
            log.error("Trying to update user with id: " + id + " who don't exist!");
            throw new ApiRequestException("User with id: " + id + " does not exist, please enter another id");
        }
        User user = opUser.get();

        //checking
        if (userDetailsRequestDTO.getFirstName() != null) {
            user.setFirstName(userDetailsRequestDTO.getFirstName());
        }
        if (userDetailsRequestDTO.getLastName() != null) {
            user.setLastName(userDetailsRequestDTO.getLastName());
        }
        if (userDetailsRequestDTO.getEmail() != null) {
            user.setEmail(userDetailsRequestDTO.getEmail());
        }

        User updatedUser = userRepository.save(user);

        UserResponseDTO returnValue = modelMapper.map(updatedUser, UserResponseDTO.class);
        log.info("User with id: " + id + " is updated");
        return returnValue;
    }

    @Override
    public boolean delete(long id) {
        log.info("Deleting user...");
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id: " + id + " is deleted");
            return true;
        } else {
            log.error("Trying to delete user with id: " + id + " who don't exist!");
            throw new ApiRequestException("User with id: " + id + " does not exist, please enter another id");
        }
    }


}
