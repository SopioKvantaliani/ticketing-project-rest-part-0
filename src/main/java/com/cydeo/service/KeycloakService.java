package com.cydeo.service;

import com.cydeo.dto.UserDTO;

import javax.ws.rs.core.Response;

public interface KeycloakService {

    Response userCreate (UserDTO dto); //this repose is providing User which is creating in KeyCloak.
    void delete (String username);

}
