package com.presentation.util;

import com.presentation.entities.User;
import com.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String addNewUser(User user) throws Exception {
        userRepository.save(user);
        return "Saved";
    }

    public Optional<User> getMyDetails(String token) throws Exception {
        String id = jwtUtil.extractUsername(token);
        return userRepository.findById(Integer.parseInt(id));
    }




    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //TODO ikke helt optimalt at user blir funnet fra en id som er parset fra en string
        Optional<User> user = userRepository.findById(Integer.parseInt(userId));
        if (!user.isPresent()) throw new UsernameNotFoundException("User not found");
        User presentUser = user.get();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(presentUser.getRole());

        return new org.springframework.security.core.userdetails.User(Integer.toString(presentUser.getId()), presentUser.getPassword(), Arrays.asList(simpleGrantedAuthority));
    }

}
