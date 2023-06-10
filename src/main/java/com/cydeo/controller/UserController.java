package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController //if we have only @Controller annotation we need to return view
@RequestMapping ("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RolesAllowed({"Manager", "Admin"}) // Totally depends on the business logic.
    public ResponseEntity <ResponseWrapper> getUsers (){
        List <UserDTO> userDTOList = userService.listAllUsers();
        return ResponseEntity.ok(new ResponseWrapper("Users are successfully retrieves", userDTOList, HttpStatus.OK));
        /*
        When we want to see status code in the body, we add in the contractor HttpStatus.Ok
         */
    }

    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity <ResponseWrapper> getUserByUserName (@PathVariable("username") String username){ //PathVariable should match the endPoint and it will be assigned to String username;
        UserDTO user = userService.findByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("User is successfully retrieved", user, HttpStatus.OK ));

    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity <ResponseWrapper> createUser (@RequestBody UserDTO user){ //UserDTO comes from API and to capture we need to use @RequestBody annotation
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body
                (new ResponseWrapper("User is successfully created", user, HttpStatus.CREATED ));

    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity <ResponseWrapper> updateUser (@RequestBody UserDTO user){
        userService.update(user);
        return ResponseEntity.ok
                (new ResponseWrapper("User is successfully updated", user, HttpStatus.OK ));
    }
    @DeleteMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity <ResponseWrapper> deleteUser (@PathVariable("username") String userName){
        userService.delete(userName);
        return ResponseEntity.ok
                (new ResponseWrapper("User is successfully deleted", userName, HttpStatus.OK ));
    }
}
