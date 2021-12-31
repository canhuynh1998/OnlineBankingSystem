package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
        repo.save(newCustomer);
    }

}
