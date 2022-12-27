package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.entity.User;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.example.demo.demoapi.repositories.UserRepository;
import com.example.demo.demoapi.services.UserService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // @PreAuthorize("hasAuthority('ADMIN')")
    @Override
    public UserResponseDTO create(UserDetailsRequestDTO userDetailsRequestDTO) {
        Optional<User> userEmail = userRepository.findByEmail(userDetailsRequestDTO.getEmail());

        if (userEmail.isPresent()) {
            throw new ApiRequestException("User with this email already exist!");
        }

        User user = modelMapper.map(userDetailsRequestDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDetailsRequestDTO.getPassword()));

        User createdUser = userRepository.save(user);
        log.info("User is created with first name: " + userDetailsRequestDTO.getFirstName() + " and last name: " + userDetailsRequestDTO.getLastName());

        return modelMapper.map(createdUser, UserResponseDTO.class);
    }

    //@PreAuthorize("hasAuthority('USER')")
    @Override
    public Page<UserResponseDTO> getAll(Pageable pageable, Predicate predicate) {
        log.info("Getting all users...");

        Page<User> users = userRepository.findAll(predicate, pageable);
        List<UserResponseDTO> userStream = users.stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(userStream, pageable, users.getTotalElements());
    }

    @Override
    public UserResponseDTO getOne(long id) {
        Optional<User> opUser = userRepository.findById(id);

        boolean exist = checkingUser(opUser, id);

        User user = opUser.get();
        log.info("Picked user with id: " + id);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO update(long id, UserDetailsRequestDTO userDetailsRequestDTO) {
        log.info("Updating  user...");
        Optional<User> opUser = userRepository.findById(id);
        boolean exist = checkingUser(opUser, id);
        User user = opUser.get();

        if (StringUtils.isNotBlank(userDetailsRequestDTO.getFirstName())) {
            user.setFirstName(userDetailsRequestDTO.getFirstName());
        }
        if (userDetailsRequestDTO.getLastName() != null) {
            user.setLastName(userDetailsRequestDTO.getLastName());
        }
        if (userDetailsRequestDTO.getEmail() != null) {
            user.setEmail(userDetailsRequestDTO.getEmail());
        }
        User updatedUser = userRepository.save(user);

        log.info("User with id: {} is updated", id);
        return modelMapper.map(updatedUser, UserResponseDTO.class);

    }

    @Override
    public boolean delete(long id) {
        log.info("Deleting user...");
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("User with id: {id} is deleted");
            return true;
        } else {
            log.error("Trying to delete user with id: {} who don't exist!", id);
            throw new ApiRequestException("User with id: {} does not exist, please enter another id");
        }
    }

    private boolean checkingUser(Optional<User> opUser, long id) {
        if (!opUser.isPresent()) {
            log.error("Trying to get user with id: {} who does not exist!", id);
            throw new ApiRequestException("User with id: {} does not exist, please enter another id");
        }
        return true;
    }
}
