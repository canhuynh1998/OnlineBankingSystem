package SelfBankingSystem.SelfBankingSystem.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping(path = "customer/{customerId}/accounts")
    public List<Account> getAllAccountsByCustomerId(@PathVariable(value = "customerId") Long customerId){
        return accountService.getAllAccountsByCustomerId(customerId);
    }

    @PostMapping(path="customer/{customerId}/accounts")
    public void createAccount(@PathVariable(value="customerId") Long customerId,
                              @RequestBody Account account){
        accountService.createAccount(customerId, account);
    }
}
