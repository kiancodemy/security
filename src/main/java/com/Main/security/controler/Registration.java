package com.Main.security.controler;
import com.Main.security.configuration.UserDetailServices;
import com.Main.security.model.Customer;
import com.Main.security.repository.CustormerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Registration {
    private final UserDetailServices userDetailServices;
    private final CustormerRepo custormerRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> hi(@RequestBody Customer request){
        try{
            String encoded=passwordEncoder.encode(request.getPwd());
            request.setPwd(encoded);
            Customer newCustomer=custormerRepo.save(request);
            if(newCustomer.getId()>0){
                return  ResponseEntity.ok().body("sucessfull created")
                ;

            }
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
        catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/by")
    public String by(){
        return "by";
    }

}
