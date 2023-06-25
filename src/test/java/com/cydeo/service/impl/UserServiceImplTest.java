package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //comes from Junit framework library, they are supporting each other.
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private TaskService taskService;
    @Mock
    private KeycloakService keycloakService;
    @InjectMocks//we are testing this class and all mocks are from here.
    private UserServiceImpl userService;
    @Spy //when we create bean ourselves we use @Spy annotation
    private UserMapper userMapper = new UserMapper(new ModelMapper()); //@Spy means, this is not mock, it will use real implementation

    User user; //create User entity
    UserDTO userDTO;  //create UserDto
    @BeforeEach //we create each time User entity and UserDto;
    void setUp(){
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("UserN");
        user.setPassWord("Abc1");
        user.setEnabled(true);
        user.setRole(new Role("Manager"));


        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setUserName("UserN");
        userDTO.setPassWord("Abc1");
        userDTO.setEnabled(true);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setDescription("Manager");

        userDTO.setRole(roleDTO);

    }

    private List<User> getUser (){
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Emily");

        return List.of(user, user2);
    }

    private List <UserDTO> getUserDTOs(){
        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setFirstName("Emily");

        return List.of(userDTO, userDTO2);

    }

    @Test
   void should_list_all_users(){
        //stub ->
        when(userRepository.findAllByIsDeletedOrderByFirstNameDesc(false)).thenReturn(getUser());
        List <UserDTO> expectedList = getUserDTOs();
        List <UserDTO> actualList = userService.listAllUsers();

       // Assertions.assertEquals(expectedList, actualList); it give error, because objects are different in the memory
        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedList); //don't compare object to object. It will compare all the fields and compares.
    }

}