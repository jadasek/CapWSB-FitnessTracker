package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;
    /**
     * Retrieves a list of all users in the system.
     *
     * @return List of {@link UserDto} objects representing all users.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    /**
     * Retrieves a simplified list of all users.
     *
     * @return List of {@link SimpleUserDto} objects representing essential information about users.
     */
    @GetMapping("/simple")
    public List<SimpleUserDto> getSimpleUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /**
     * Searches for users based on optional criteria such as ID, first name, last name, birthdate, and email.
     * Any combination of parameters can be used for the search.
     *
     * @param id        ID of the user (optional)
     * @param firstName First name of the user (optional)
     * @param lastName  Last name of the user (optional)
     * @param birthdate Birthdate of the user (optional)
     * @param email     Email of the user (optional)
     * @return List of {@link UserDto} objects that match the search criteria, or an empty list if no criteria are provided.
     */

    @GetMapping("/search")
    public List<UserDto> getUsers(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) LocalDate birthdate,
            @RequestParam(required = false) String email) {

        return userService.getUsers(id, firstName, lastName, birthdate, email)
                        .stream()
                        .map(userMapper::toDto)
                        .toList();
    }

    /**
     * Creates a new user in the system based on the provided user details.
     *
     * @param userDto The {@link UserDto} object containing the user's details.
     * @return The created {@link User} object with its assigned ID and details.
     * @throws InterruptedException if the process is interrupted.
     */
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        User passedUser = userMapper.toEntity(userDto);
        User createdUser = userService.createUser(passedUser);
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        return createdUser;
    }

    /**
     * Deletes a user by their ID.
     *
     * This endpoint allows for the removal of a user from the system using their unique ID.
     * Upon successful deletion, it returns a 204 No Content
     * 
     * @param id the ID of the user to be deleted (must not be null)
     * @throws UserNotFoundException if no user with the specified ID exists
     */
    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }

    /**
     * Retrieves a list of users by their email.
     * If the email is provided and matches a user in the system, the user details are returned.
     * If no user is found, an empty list is returned.
     *
     * @param email The email address of the user to be searched (optional).
     * @return A list of {@link UserEmailDto} objects matching the provided email.
     *         If no user is found, returns an empty list.
     */
    @GetMapping("/email")
    public List<UserEmailDto> getUserbyEmail(@RequestParam(required = false) String email) {
            return userService.getUserByEmail(email)
                    .stream()
                    .map(userMapper::toUserEmailDto)
                    .toList();
    }

    /**
     * Endpoint to retrieve users born before a specified date.
     *
     * This method returns a list of users whose birthdate is earlier than the provided date.
     * The birthdate is passed as a path variable and must be in the format 'YYYY-MM-DD'.
     *
     * @param birthdate The date to compare against users' birthdates. It must be provided in ISO format (YYYY-MM-DD).
     * @return A list of {@link UserDto} objects representing users born before the specified date.
     */
    @GetMapping("/bydate/{birthdate}")
    public List<UserDto> getUsersbyDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate) {
        return userService.findAllUsers()
                        .stream()
                        .filter(user -> user.getBirthdate().isBefore(birthdate))
                        .map(userMapper::toDto)
                        .toList();
    }

    /**
     * Endpoint to update an existing user by their ID.
     *
     * This method allows updating one or more attributes of a user. The user is identified by their ID,
     * and the fields to be updated are passed in the request body as a {@link UserDto}.
     *
     * @param id      The ID of the user to be updated.
     * @param userDto The data containing updated user information.
     * @return The updated {@link UserDto} object.
     * @throws UserNotFoundException if the user with the given ID does not exist.
     */
    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(id, userMapper.toEntity(userDto));
        return userMapper.toDto(updatedUser);
    }

}

