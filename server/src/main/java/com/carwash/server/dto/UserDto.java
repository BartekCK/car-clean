package com.carwash.server.dto;

import com.carwash.server.models.User;
import com.carwash.server.models.UserPrincipal;
import lombok.Value;
import javax.validation.constraints.Email;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class UserDto {
    private Long id;
    private String username;
    @Email
    private String email;
    private String phone;
    private List<String> roles;

    public static UserDto build(User user) {
        List<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), roles);
    }
    public static UserDto build(UserPrincipal user) {
        List<String> roles = user.getAuthorities().stream().map(role -> role.getAuthority()).collect(Collectors.toList());
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), roles);
    }
}
