package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import java.util.Collections;

interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query searching users by email address. It matches by exact match.
     *
     * @param email email of the user to search
     * @return {@link Optional} containing found user or {@link Optional#empty()} if none matched
     */
    default Optional<User> findByEmail(String email) {
        return findAll().stream()
                        .filter(user -> Objects.equals(user.getEmail(), email))
                        .findFirst();
    }

    /**
     * Finds users by various criteria (ID, first name, last name, birthdate, email).
     *
     * @param id        ID of the user (optional)
     * @param firstName First name of the user (optional)
     * @param lastName  Last name of the user (optional)
     * @param birthdate Birthdate of the user (optional)
     * @param email     Email of the user (optional)
     * @return List of users matching the criteria
     */
    default List<User> findByCriteria(Long id, String firstName, String lastName, LocalDate birthdate, String email) {
        return findAll().stream()
                .filter(user -> id == null || Objects.equals(user.getId(), id))
                .filter(user -> firstName == null || Objects.equals(user.getFirstName(), firstName))
                .filter(user -> lastName == null || Objects.equals(user.getLastName(), lastName))
                .filter(user -> birthdate == null || Objects.equals(user.getBirthdate(), birthdate))
                .filter(user -> email == null || Objects.equals(user.getEmail(), email))
                .collect(Collectors.toList());
    }
    
}
