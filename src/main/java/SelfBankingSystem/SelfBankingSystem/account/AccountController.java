package SelfBankingSystem.SelfBankingSystem.account;

import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private Customer getCurrentLoggedInUser(){
        return (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping
    public List<Account> getAllAccountsByCustomerId(){
        Customer target = getCurrentLoggedInUser();
        return accountService.getAllAccountsByCustomerId(target.getId());
    }

    @PostMapping
    public void createAccount(@RequestBody Account account){
        Customer target = getCurrentLoggedInUser();
        accountService.createAccount(target.getId(), account);
    }

    @PutMapping(path = "deposit/{accountType}")
    public void depositAccount(@PathVariable(required = true) Integer accountType,
                               @RequestParam(required = true) Integer amount){
        Customer target = getCurrentLoggedInUser();
        accountService.depositAccount(target.getId(), accountType, amount);
    }

    @PutMapping(path = "withdraw/{accountType}")
    public void withdrawAccount(@PathVariable(required = true) Integer accountType,
                                @RequestParam(required = true) Integer amount){
        Customer target = getCurrentLoggedInUser();
        accountService.withdrawAccount(target.getId(), accountType, amount);
    }

    @PutMapping(path = "active/{accountType}")
    public void lockOrUnlockAccount(@PathVariable(required = true) Integer accountType){
        Customer target = getCurrentLoggedInUser();
        accountService.lockOrUnlockAccount(target.getId(), accountType);
    }

    @DeleteMapping(path = "{accountType}")
    public void deleteAccount(@PathVariable(required = true) Integer accountType){
        Customer target = getCurrentLoggedInUser();
        accountService.deleteAccount(target.getId(), accountType);
    }


}
