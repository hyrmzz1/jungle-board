package com.example.jungleboard.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    public Integer UserId;
    public UserDto (User user){
        this.UserId = user.userId;
    }

}
