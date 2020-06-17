package com.steppedua.restapplication.controller;

import com.steppedua.restapplication.entity.User;
import com.steppedua.restapplication.repository.RoleRepository;
import com.steppedua.restapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "get/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/list", headers = "Accept=application/json")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list = userService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/add", headers = "Accept=application/json")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/edit/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id,
                                           @Valid @RequestBody User currentUser) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setName(currentUser.getName());
        user.setLogin(currentUser.getLogin());
        user.setPassword(currentUser.getPassword());
        user.setRoles(new HashSet<>(user.getRoles()));


        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}", headers = "Accept=application/json")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
