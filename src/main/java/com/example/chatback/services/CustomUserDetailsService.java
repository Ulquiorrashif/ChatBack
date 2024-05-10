//package com.example.chatback.services;
//
//
//import com.example.chatback.entity.Users;
//import com.example.chatback.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final PasswordEncoder passwordEncoder;
//
//    private  final UserRepository userRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       Users users = userRepository.findByEmail(username);
//        System.out.println(username+" "+users.getPassword());
//        return users ;
//    }
//
//
//
//}
