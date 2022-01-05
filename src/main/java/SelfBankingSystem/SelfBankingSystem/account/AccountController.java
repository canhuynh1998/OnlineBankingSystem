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

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping(path = "{customerId}")
    public List<Account> getAllAccountsByCustomerId(@PathVariable(value = "customerId") Long customerId){
        return accountService.getAllAccountsByCustomerId(customerId);
    }

    @PostMapping(path="{customerId}")
    public void createAccount(@PathVariable(value="customerId") Long customerId,
                              @RequestBody Account account){
        accountService.createAccount(customerId, account);
    }

    @PutMapping(path = "{customerId}/deposit")
    public void depositAccount(@PathVariable(value="customerId") Long customerId,
                               @RequestParam(required = true) Integer accountType,
                               @RequestParam(required = true) Integer amount){
        accountService.depositAccount(customerId, accountType, amount);
    }

    @PutMapping(path = "{customerId}/withdraw")
    public void withdrawAccount(@PathVariable(value="customerId") Long customerId,
                               @RequestParam(required = true) Integer accountType,
                               @RequestParam(required = true) Integer amount){
        accountService.withdrawAccount(customerId, accountType, amount);
    }

    @PutMapping(path = "{customerId}/active")
    public void lockOrUnlockAccount(@PathVariable(value="customerId") Long customerId,
                                @RequestParam(required = true) Integer accountType){
        accountService.lockOrUnlockAccount(customerId, accountType);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteAccount(@PathVariable(value="customerId") Long customerId,
                                @RequestParam(required=true) Integer accountType){
        accountService.deleteAccount(customerId, accountType);
    }
}
