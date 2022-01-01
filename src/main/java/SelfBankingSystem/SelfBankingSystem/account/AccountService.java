package SelfBankingSystem.SelfBankingSystem.account;


import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import SelfBankingSystem.SelfBankingSystem.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository){
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

}
