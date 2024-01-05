package com.example.jungleboard.user;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/{UserId}/search")
    public Response apiGetUser(@PathVariable("MemberId") @Valid Integer memberId, @RequestParam("q") Integer boardId @RequestBody UserDto userDto){
        ResponseEntity getUser = userService.getUserById(memberId);

    }
}


/*
* {
*   "userId":"hyerim",
*   "userName":"양혜림"
* }
*
* */