package SelfBankingSystem.SelfBankingSystem.account;
import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import SelfBankingSystem.SelfBankingSystem.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

        if(accountBalance < 0){
            throw new IllegalStateException("Insufficient Fund!");
        }

        if(accountType != 0 && accountType != 1 ){
            throw new IllegalStateException("Invalid Account Type");
        }

        account.setCustomer(targetCustomer);
        accountRepository.save(account);

    }

    @Transactional
    public void depositAccount(Long customerId, Integer accountType, Integer amount) {
        putRequestHelper(customerId, accountType, amount);
        Optional<Account> potentialAccount = accountRepository.findByType(accountType);
        Account targetAccount = potentialAccount.get();
        targetAccount.setBalance(targetAccount.getBalance() + amount);
        accountRepository.save(targetAccount);
    }

    @Transactional
    public void withdrawAccount(Long customerId, Integer accountType, Integer amount) {
        putRequestHelper(customerId, accountType, amount);
        Optional<Account> potentialAccount = accountRepository.findByType(accountType);
        Account targetAccount = potentialAccount.get();
        if(amount > targetAccount.getBalance()){
            throw new IllegalStateException("Insufficient Fund to Withdraw!");
        }
        targetAccount.setBalance(targetAccount.getBalance() - amount);
        accountRepository.save(targetAccount);
    }

    private void putRequestHelper(Long customerId, Integer accountType, Integer amount) {
        // TODO: Validate the input for deposit and withdraw

        customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("Customer doesn't exist"));

        if (accountType != 0 && accountType != 1) {
            throw new IllegalStateException("Invalid Argument!");
        }
        if (amount < 0) {
            throw new IllegalStateException("Invalid Argument!");
        }

        Optional<Account> potentialAccount = accountRepository.findByType(accountType);

        if (!potentialAccount.isPresent()) {
            String type = accountType == 0 ? "Checking" : "Saving";
            throw new IllegalStateException(type + " Account doesn't exist!");
        }
    }

    @Transactional
    public void deleteAccount(Long customerId, Integer accountType) {
        if(accountType != 0 && accountType != 1){
            throw new IllegalStateException("Invalid Argument!");
        }
        Customer targetCustomer = customerRepository.findById(customerId)
                .orElseThrow(()->new IllegalStateException("Customer doesn't exist"));
        Optional<Account> potentialAccount = accountRepository.findByCustomerIdAndType(customerId,accountType);
        if(!potentialAccount.isPresent()){
            String type = accountType == 0 ? "Checking" : "Saving";
            throw new IllegalStateException(type + " Account doesn't exist!");
        }

        Account targetAccount = potentialAccount.get();
        accountRepository.delete(targetAccount);
    }

    public void lockOrUnlockAccount(Long customerId, Integer accountType) {
        putRequestHelper(customerId, accountType, 0);
        Optional<Account> potentialAccount = accountRepository.findByType(accountType);
        Account targetAccount = potentialAccount.get();
        targetAccount.setActive(-1* targetAccount.getActive());
        accountRepository.save(targetAccount);
    }
}
