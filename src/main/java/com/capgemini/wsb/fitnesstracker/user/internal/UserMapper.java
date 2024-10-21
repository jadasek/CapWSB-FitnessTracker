package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                           user.getFirstName(),
                           user.getLastName(),
                           user.getBirthdate(),
                           user.getEmail());
    }

    User toEntity(UserDto userDto) {
        return new User(
                        userDto.firstName(),
                        userDto.lastName(),
                        userDto.birthdate(),
                        userDto.email());
    }

    UserEmailDto toUserEmailDto(User user) {
        return new UserEmailDto(
                        user.getId(),
                        user.getEmail());
    }

    SimpleUserDto toSimpleDto(User user) {
        return new SimpleUserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName());
    }

}
