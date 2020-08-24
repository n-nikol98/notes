package com.notes.service;

import com.notes.repository.UserRepository;
import com.notes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;

/**
 *
 * Spring Security required Service.
 *
 * */

@Service
class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) {
        final Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);

        final User user = optionalUser.get();

        /*
        *
        * Currently, the Users of this application do not have a pre-defined role.
        *
        * */
        user.setGrantedAuthorities(new HashSet<GrantedAuthority>()
        {{ add(new SimpleGrantedAuthority("no_role")); }});

        return user;
    }
}