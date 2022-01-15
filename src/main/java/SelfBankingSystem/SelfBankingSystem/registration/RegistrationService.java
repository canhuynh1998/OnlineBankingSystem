package SelfBankingSystem.SelfBankingSystem.registration;

import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import SelfBankingSystem.SelfBankingSystem.customer.CustomerService;
import SelfBankingSystem.SelfBankingSystem.security.config.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final CustomerService customerService;

    public String register(RegistrationRequest request){
        return customerService.register(
                new Customer(
                        request.getName(),
                        request.getSex(),
                        request.getDob(),
                        UserRole.USER,
                        request.getUserName(),
                        request.getPassword(),
                        request.getPin()
                )

        );
    }


    public String register_admin(RegistrationRequest request){
        return customerService.register(
                new Customer(
                        request.getName(),
                        request.getSex(),
                        request.getDob(),
                        UserRole.ADMIN,
                        request.getUserName(),
                        request.getPassword(),
                        request.getPin()
                )

        );
    }

}
