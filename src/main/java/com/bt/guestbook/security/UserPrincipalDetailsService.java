package com.bt.guestbook.security;

import com.bt.guestbook.model.CustomUserDetails;
import com.bt.guestbook.model.User;
import com.bt.guestbook.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public UserPrincipalDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    User user = this.userRepository.findByUsername(s);
    return new CustomUserDetails(user);
  }
}
