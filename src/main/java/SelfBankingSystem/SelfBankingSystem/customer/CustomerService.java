package SelfBankingSystem.SelfBankingSystem.customer;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static java.lang.Character.toLowerCase;

@Service
@AllArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository repo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Customer> getCustomers(){
        return repo.findAll();
    }

    public Customer getCustomer(Long id){return repo.findById(id).orElseThrow(()->new IllegalStateException("ID not found"));}

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return repo.findByUserName(name).orElseThrow(()->new IllegalStateException("User doesn't exist"));
    }

    public String register(Customer newCustomer){
        // Input validation
        Boolean existed = repo.findByUserName(newCustomer.getUsername()).isPresent();
        if(existed){
            throw new IllegalStateException("User existed!");
        }

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

        String encodedPassword = bCryptPasswordEncoder.encode(newCustomer.getPassword());
        newCustomer.setPassword(encodedPassword);
        repo.save(newCustomer);
        return "User created with id: "+newCustomer.getId();
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
