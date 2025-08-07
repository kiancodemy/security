package com.Main.security.configuration;


import com.Main.security.model.Customer;
import com.Main.security.repository.CustormerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServices implements UserDetailsService {

    private final CustormerRepo custormerRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer= custormerRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(customer.getRole())
        );
        return new User(customer.getEmail(),customer.getPwd(),authorities);


    }
}
