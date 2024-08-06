package com.learnjava.BankingApp.model;

import com.learnjava.BankingApp.model.User;
import com.learnjava.BankingApp.model.UserPrincipal;
import com.learnjava.BankingApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()){
            System.out.println("Couldn't find user");
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
        return new UserPrincipal(user.get());
    }
}
