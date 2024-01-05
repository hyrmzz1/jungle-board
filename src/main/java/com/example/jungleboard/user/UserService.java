package com.example.jungleboard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> getUserById(Integer memberId){
        Optional<User> user = userRepository.findById(memberId);
        ResponseEntity<User> newUser = new ResponseEntity<>(user,201);
        return newUser;
    }

}
