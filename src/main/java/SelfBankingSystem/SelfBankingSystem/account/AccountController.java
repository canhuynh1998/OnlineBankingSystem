package SelfBankingSystem.SelfBankingSystem.account;

import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(path = "all")
    @PreAuthorize("hasAnyRole('ADMIN')")
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

    @PostMapping(path = "create/{accountType}")
    public void createAccount(@PathVariable(required = true) Integer accountType){
        Customer target = getCurrentLoggedInUser();
        Account account = new Account(0L, accountType);
        System.out.println("Hit with "+accountType);
        accountService.createAccount(target.getId(), account);
    }

    @PutMapping(path = "deposit/{accountType}")
    public void depositAccount(@PathVariable(required = true) Integer accountType,
                               @RequestBody(required = true) String amount){
        Customer target = getCurrentLoggedInUser();
        System.out.println("Amount before parsing: "+amount);
        Integer amount_ = Integer.parseInt(amount);
        accountService.depositAccount(target.getId(), accountType, amount_);
    }

    @PutMapping(path = "withdraw/{accountType}")
    public void withdrawAccount(@PathVariable(required = true) Integer accountType,
                                @RequestBody(required = true) String amount){
        Customer target = getCurrentLoggedInUser();
        Integer amount_ = Integer.parseInt(amount);
        accountService.withdrawAccount(target.getId(), accountType, amount_);
    }

    @PutMapping(path = "activate/{accountType}")
    public void lockOrUnlockAccount(@PathVariable(required = true) Integer accountType){
        Customer target = getCurrentLoggedInUser();
        accountService.lockOrUnlockAccount(target.getId(), accountType);
    }

    @DeleteMapping(path = "delete/{accountType}")
    public void deleteAccount(@PathVariable(required = true) Integer accountType){
        Customer target = getCurrentLoggedInUser();
        accountService.deleteAccount(target.getId(), accountType);
    }


}
