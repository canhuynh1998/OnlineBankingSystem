package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


import static java.lang.Character.toLowerCase;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    @Autowired
    public CustomerService(CustomerRepository repo){
        this.repo = repo;
    }

    public List<Customer> getCustomers(){
        return repo.findAll();
    }

    public void addNewCustomer(Customer newCustomer){
        // Input validation
        Character newCustomerGender = toLowerCase(newCustomer.getSex());
        LocalDate newCustomerDob = newCustomer.getDob();
        Integer newCustomerPin = newCustomer.getPin();

        if(!(newCustomerGender.equals('m') || newCustomerGender.equals('f'))){
            throw new IllegalStateException("Invalid gender!");
        }

        if(newCustomerDob.isAfter(LocalDate.now())){
            throw new IllegalStateException("Invalid DOB!");
        }

        if(newCustomerPin < 0){
            throw new IllegalStateException("Invalid pin!");
        }

        repo.save(newCustomer);
    }

    @Transactional
    public void updateCustomer(Long targetId, Integer newPin){
        Customer targetCustomer = repo.findById(targetId)
                .orElseThrow(()->new IllegalStateException("Customer doesn't exist!"));
        if(newPin != null && newPin > 0){
            targetCustomer.setPin(newPin);
        }
    }

    public void deleteCustomer(Long targetId) {
        Customer targetCustomer = repo.findById(targetId)
                .orElseThrow(()->new IllegalStateException("Customer doesn't exist!"));
        repo.delete(targetCustomer);
    }
}
