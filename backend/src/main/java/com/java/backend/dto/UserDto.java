package com.java.backend.dto;

import com.java.backend.enums.Gender;
import com.java.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends AbstractDto {
    private String email;
    private String name;
    private String avatar;
    private String phone;
    private Gender gender;
    private Role role;
}
