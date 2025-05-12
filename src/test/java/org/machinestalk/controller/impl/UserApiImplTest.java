package org.machinestalk.controller.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.machinestalk.api.UserApi;
import org.machinestalk.api.dto.AddressDto;
import org.machinestalk.api.dto.UserDto;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.api.impl.UserApiImpl;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.service.UserService;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
public class UserApiImplTest {

    @Mock
    private UserApiImpl userApi;

    @Mock
    UserService userService;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void Should_RegisterNewUser_When_RegisterUser() {

        AddressDto addressDto = new AddressDto();
        addressDto.setCity("Rue de Voltaire");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setFirstName("Jack");
        userRegistrationDto.setLastName("Sparrow");
        userRegistrationDto.setDepartment("RH");
        userRegistrationDto.setPrincipalAddress(addressDto);

        Address address = new Address();
        address.setCity("Rue de Voltaire");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Jack");
        user.setLastName("Sparrow");
        user.setDepartment(new Department("RH"));
        user.setAddresses(Set.of(address));

        UserDto userDto = new UserDto();
        userDto.setId("01");
        UserDto.UserInfos userInfos = new UserDto.UserInfos("Jack","Sparrow", "RH", List.of("address") );
        userDto.setUserInfos(userInfos);

        UserDto result = userApi.register(userRegistrationDto);
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getUserInfos(), result.getUserInfos());
    }

    @Test
    void Should_RetrieveUserByTheGivenId_When_findUserById(){

        Long userId = 1L;
        User user = new User();
        user.setId(1L);
        user.setFirstName("Jack)");
        user.setLastName("Sparrow)");

        UserDto userDto = new UserDto();
        UserDto.UserInfos userInfos = new UserDto.UserInfos("Jack","Sparrow", "RH", List.of("address") );
        userDto.setUserInfos(userInfos);

        when(userService.getById(userId)).thenReturn(Mono.just(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        // When
        Mono<UserDto> resultMono = userApi.findUserById(userId);

        // Then
        UserDto result = resultMono.block();
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getUserInfos(), result.getUserInfos());
    }
}
