package com.gegessen.controller;


import com.gegessen.dto.user.UserAllPropertiesDto;
import com.gegessen.service.user.IUserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.Set;

import static com.gegessen.util.Constants.*;
import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/config/api/v1/users")
public final class UserController {

    private static final Logger LOGGER = getLogger(UserController.class);
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<UserAllPropertiesDto>> getAll() {
        LOGGER.info(GET_ALL_USERS_MESSAGE);
        return ok(userService.findAll());
    }

    @GetMapping(value = "/{email}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> getOne(@PathVariable("email") String email) {
        LOGGER.info(format(FIND_USER_BY_EMAIL_MESSAGE, email));
        return ok(userService.findOne(email));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> create(@Valid @RequestBody UserAllPropertiesDto user) {
        LOGGER.info(format(CREATE_USER_MESSAGE, user.getEmail()));
        return status(CREATED).body(userService.create(user));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable("email") String email) {
        LOGGER.info(format(DELETE_USER_BY_EMAIL_MESSAGE, email));
        userService.delete(email);
        return status(NO_CONTENT).build();
    }

    @PutMapping(value = "/{email}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> update(@Valid @RequestBody UserAllPropertiesDto user, @PathVariable("email") String email) {
        LOGGER.info(format(UPDATE_USER_BY_EMAIL_MESSAGE, email));
        return ok(userService.update(user, email));
    }

    @PostMapping(value = "/login",consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAllPropertiesDto> login(@Valid @RequestBody UserAllPropertiesDto user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();

        return ResponseEntity.ok(userService.findOne(id));
    }

    @PostMapping(value = "/register/email",consumes = APPLICATION_JSON_VALUE,produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerWithEmail(@Valid @RequestBody UserAllPropertiesDto user){

        userService.sendVerificationEmail(user.getEmail());

        return status(NO_CONTENT).build();
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(){

        return ResponseEntity.ok().build();
    }


}


