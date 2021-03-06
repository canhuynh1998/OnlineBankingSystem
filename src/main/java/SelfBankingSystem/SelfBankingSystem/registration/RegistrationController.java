package SelfBankingSystem.SelfBankingSystem.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        System.out.println("In here");
        return registrationService.register(request);
    }

    @PostMapping("admin")
    public String register_admin(@RequestBody RegistrationRequest request){
        return registrationService.register_admin(request);
    }
}
