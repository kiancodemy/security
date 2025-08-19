package com.Main.security.configuration.AuthenticService;
import com.Main.security.model.Customer;
import com.Main.security.repository.CustormerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MainUserDetailService implements UserDetailsService {
    private final CustormerRepo custormerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Customer customer=custormerRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+"is not found"));
      List<GrantedAuthority> roles=List.of(new SimpleGrantedAuthority(customer.getRole()));
      return new User(customer.getEmail(),customer.getPwd(),roles);

    }}
