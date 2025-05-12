package org.machinestalk.service.impl;
import org.machinestalk.api.dto.UserRegistrationDto;
import org.machinestalk.domain.Address;
import org.machinestalk.domain.Department;
import org.machinestalk.domain.User;
import org.machinestalk.repository.UserRepository;
import org.machinestalk.service.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public User registerUser(final UserRegistrationDto userRegistrationDto) {

    User user = new User();
    return userRepository.save(user);
  }

  @Override
  public Mono<User> getById(final long id) {
    return Mono.justOrEmpty(userRepository.findById(id));
  }
}