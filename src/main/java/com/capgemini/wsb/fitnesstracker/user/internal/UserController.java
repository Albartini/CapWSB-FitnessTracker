package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/simple")
    public List<UserSimpleDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userMapper.toDto(userService.getUser(id).orElseThrow());
    }

    @GetMapping("/email")
    public List<UserDto> getUserByEmail(@RequestParam("email") String email) {
        var test = userService.getUserByEmail(email);
        return Arrays.asList(userMapper.toDto(userService.getUserByEmail(email).orElseThrow()));
    }

    @GetMapping("/older/{time}")
    public List<UserDto> getUserOlderThan(@PathVariable LocalDate time) {
        return userService.findAllUsers()
                .stream().filter(u -> u.getBirthdate().isBefore(time))
                .map(userMapper::toDto)

                .toList();
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long userId) {
        userService.deleteUserById(userId);
    }



    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        var user = userService.createUser(userMapper.toEntity(userDto));
        return userService.getUser(user.getId()).orElseThrow();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody UserDto userUpdateDto) throws InterruptedException {
        System.out.println("User with e-mail: " + userUpdateDto.email() + "passed to the update request");
        return userService.updateUserById(userId, userUpdateDto);
    }

}