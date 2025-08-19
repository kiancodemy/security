package com.Main.security.controler;
import com.Main.security.model.Customer;
import com.Main.security.repository.CustormerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users") //
@RestController
@RequiredArgsConstructor
public class UserController {
    private final CustormerRepo custormerRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer){

        try{
            String newPassword=passwordEncoder.encode(customer.getPwd());
            customer.setPwd(newPassword);
            Customer customer1=custormerRepo.save(customer);
            if(customer1.getId()>0){
                return ResponseEntity.ok().body("created");
              
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not found");

            }

        }
        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @GetMapping("/notice")
    public ResponseEntity<String> notice(){
        try{
            return ResponseEntity.ok().body("notice");
        }


        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @GetMapping("/user")
    public ResponseEntity<List<Customer>> users(){
        try{
            List<Customer> findAl=custormerRepo.findAll();
            return ResponseEntity.ok().body(findAl);
        }


        catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
        }
    }
}
