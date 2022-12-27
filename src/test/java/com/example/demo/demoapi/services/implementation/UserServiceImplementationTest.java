package com.example.demo.demoapi.services.implementation;

import com.example.demo.demoapi.dtos.request.UserDetailsRequestDTO;
import com.example.demo.demoapi.dtos.response.UserResponseDTO;
import com.example.demo.demoapi.exceptions.ApiRequestException;
import com.example.demo.demoapi.repositories.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.demoapi.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserServiceImplementationTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImplementation userService;


    @Test
    void canCreateUser() {
        // GIVEN
        UserDetailsRequestDTO userDetailsRequestDTO = createUserRequestModel();

        User user = new User(userDetailsRequestDTO);
        user.setId(12345121313L);
        UserResponseDTO response = new UserResponseDTO(user);

        //WHEN
        when(userRepository.findByEmail(userDetailsRequestDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDetailsRequestDTO.getPassword())).thenReturn("rale");
        when(modelMapper.map(userDetailsRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(response);

        //ACTION
        UserResponseDTO actual = userService.create(userDetailsRequestDTO);

        //THEN
        assertNotNull(actual);
        assertEquals("RADOSGOLUBOVIC", actual.getFirstName());

    }

    @Test
    void canCreateUserWithSameEmail() {
        // GIVEN
        UserDetailsRequestDTO userDetailsRequestDTO = createUserRequestModel();
        User user = new User(userDetailsRequestDTO);

        // WHEN
        when(userRepository.findByEmail(userDetailsRequestDTO.getEmail())).thenReturn(Optional.of(user));

        // THEN
        assertThrows(ApiRequestException.class, () -> userService.create(userDetailsRequestDTO));
    }

    @Test
    void canGetAllUsers() {
        // GIVEN
        List<User> users = createUsersList();
        Page<User> usersPage = new PageImpl<>(users);
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        // WHEN
        when(userRepository.findAll(any(Predicate.class), any(Pageable.class))).thenReturn(usersPage);
        when(modelMapper.map(any(User.class), eq(UserResponseDTO.class))).thenReturn(userResponseDTO);

        // ACTION
        Page<UserResponseDTO> response = userService.getAll(PageRequest.of(0, 10), new BooleanBuilder());

        // THEN
        List<User> newUsers = new ArrayList<>();
        newUsers.addAll(usersPage.getContent());
        assertNotNull(response);
        assertEquals(3, response.getContent().size());
        assertEquals("Rados", newUsers.get(0).getFirstName());
    }

    @Test
    void getOneUserById() {
        // GIVEN
        User user = new User(43, "Rados", "Golubovic", "rexample@gmail.com", "example", "1234", "USER");
        UserResponseDTO userResponseDTO = new UserResponseDTO(user);

        // WHEN
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);

        // ACTION
        UserResponseDTO response = userService.getOne(user.getId());

        // THEN
        assertNotNull(response);
        //assertEquals();
        verify(userRepository).findById(user.getId());
    }

    @Test
    void tryingToGetUserWhoDoesNotExist() {
        // GIVEN
        User user = new User(6543, "Rados", "Golubovic", "rexample@gmail.com", "example", "1234", "USER");

        // WHEN
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // THEN
        assertThrows(ApiRequestException.class, () -> userService.getOne(user.getId()));
    }

    @Test
    void tryToUpdateUserFirstNameWithoutLastNameAndEmail() {
        // GIVEN
        User user = createUser();

        UserDetailsRequestDTO userRequest = new UserDetailsRequestDTO();
        userRequest.setFirstName("FIRST NAME");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setFirstName("FIRST NAME");
        userResponseDTO.setLastName(userRequest.getLastName());
        userResponseDTO.setEmail(userRequest.getEmail());

        // WHEN
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);
        when(userRepository.save(user)).thenReturn(user);

        // ACTION
        UserResponseDTO response = userService.update(user.getId(), userRequest);

        // THEN
        assertNotNull(response);
        assertEquals(response.getFirstName(), userRequest.getFirstName());
        assertEquals(response.getLastName(), null);
        assertEquals(response.getEmail(), null);

    }

    @Test
    void tryToUpdateUserLastNameWithoutFirstNameAndEmail() {
        // GIVEN
        User user = createUser();
        UserDetailsRequestDTO userRequest = new UserDetailsRequestDTO();
        userRequest.setLastName("LAST NAME");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setLastName(userRequest.getLastName());
        userResponseDTO.setFirstName(userRequest.getFirstName());
        userResponseDTO.setEmail(userRequest.getEmail());

        // WHEN
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);
        when(userRepository.save(user)).thenReturn(user);

        // ACTION
        UserResponseDTO responseDTO = userService.update(user.getId(), userRequest);

        // THEN
        assertNotNull(responseDTO);
        assertEquals(responseDTO.getLastName(), userRequest.getLastName());
        assertEquals(responseDTO.getFirstName(), null);
        assertEquals(responseDTO.getFirstName(), null);
    }

    @Test
    void tryToUpdateUserEmailWithoutFirstNameAndLastName() {
        // GIVEN
        User user = createUser();
        UserDetailsRequestDTO userRequest = new UserDetailsRequestDTO();
        userRequest.setEmail("email@example");

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setLastName(userRequest.getLastName());
        userResponseDTO.setFirstName(userRequest.getFirstName());
        userResponseDTO.setEmail(userRequest.getEmail());

        // WHEN
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserResponseDTO.class)).thenReturn(userResponseDTO);
        when(userRepository.save(user)).thenReturn(user);

        // ACTION
        UserResponseDTO responseDTO = userService.update(user.getId(), userRequest);

        // THEN
        assertNotNull(responseDTO);
        assertEquals(responseDTO.getEmail(), userRequest.getEmail());
        assertEquals(responseDTO.getFirstName(), null);
        assertEquals(responseDTO.getLastName(), null);
    }

    @Test
    void tryToDeleteUser() {
        // GIVEN
        User user = createUser();

        // WHEN
        when(userRepository.existsById(user.getId())).thenReturn(true);

        // ACTION
//       UserResponseDTO responseDTO=userService.getOne(user.getId());

        boolean deleted = userService.delete(user.getId());

//        assertThrows(ApiRequestException.class, () -> userService.getOne(user.getId()));

        // THEN
        assertTrue(deleted);

        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void tryDeleteUserWhoDoesNotExist() {
        // GIVEN
        User user = createUser();

        // WHEN
        when(userRepository.existsById(user.getId())).thenReturn(false);

        // THEN
        assertThrows(ApiRequestException.class, () -> userService.delete(user.getId()));
    }


    private UserDetailsRequestDTO createUserRequestModel() {
        UserDetailsRequestDTO userDetailsRequestDTO = new UserDetailsRequestDTO();
        userDetailsRequestDTO.setEmail("radosmailmail@gmail.com");
        userDetailsRequestDTO.setFirstName("RADOSGOLUBOVIC");
        userDetailsRequestDTO.setLastName("GOLUBOVIC");
        userDetailsRequestDTO.setUsername("rale");
        userDetailsRequestDTO.setPassword("rale");
        userDetailsRequestDTO.setRole("USER");
        return userDetailsRequestDTO;
    }

    private List<User> createUsersList() {
        List<User> users = new ArrayList<>();
        users.add(new User(123441231L, "Rados", "Golubovic", "radosrados@example.com", "rados", "rados", "USER"));
        users.add(new User(1241231L, "Marko", "Markovic", "markovicm@example.com", "marko", "marko", "USER"));
        users.add(new User(1234412L, "Petar", "Petrovic", "petar@example.com", "petar", "petar", "USER"));
        return users;
    }

    private User createUser() {
        User user = new User();
        user.setFirstName("RADOS");
        user.setLastName("GOLUBOVIC");
        user.setEmail("rados@example.com");
        user.setId(1234441);
        return user;
    }

}