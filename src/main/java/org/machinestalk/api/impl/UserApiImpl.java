package org.machinestalk.api.impl;

import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class UserApiImpl implements UserApi {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserApiImpl(final UserService userService, ModelMapper modelMapper) {

        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto register(@RequestBody @NotNull @Valid final UserRegistrationDto userRegistrationDto) {
        User user = userService.registerUser(userRegistrationDto);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public Mono<UserDto> findUserById(@PathVariable long id) {
        Mono<User> monoUser = userService.getById(id);

        return monoUser.map(user -> modelMapper.map(user, UserDto.class));
    }
}
