package SelfBankingSystem.SelfBankingSystem.account;


import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import SelfBankingSystem.SelfBankingSystem.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<Account> getAllAccountsByCustomerId(Long customerId){
        return accountRepository.findByCustomerId(customerId);
    }

    public void createAccount(Long customerId, Account account) {
        Customer targetCustomer = customerRepository.findById(customerId)
                .orElseThrow(()->new IllegalStateException("Customer doesn't exist"));

        Optional<Account> accountNumber = accountRepository.findByNumber(account.getNumber());
        Optional<Account> accountId = accountRepository.findById(account.getId());

        if(accountNumber.isPresent() || accountId.isPresent()){
            throw new IllegalStateException("Account Number/ID existed!");
        }

        Long accountBalance = account.getBalance();
        Integer accountType = account.getType();
        Integer accountStatus = account.getActive();
        if(accountBalance < 0){
            throw new IllegalStateException("Insufficient Fund!");
        }

        if(accountType != 0 || accountType != 1){
            throw new IllegalStateException("Invalid Account Type!");
        }

        if(accountStatus != 0 || accountType != 1){
            throw new IllegalStateException("Invalid Account Status!");
        }

        targetCustomer.getAccounts().add(account);
        accountRepository.save(account);

    }
}
