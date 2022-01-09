package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService service){
        this.customerService = service;
    }

    @GetMapping(path = "all")
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }

    private Customer getCurrentLoggedInUser(){
        return (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    @GetMapping
    public Customer getCustomer(){
        Customer target = getCurrentLoggedInUser();
        return customerService.getCustomer(target.getId());
    }
    @PostMapping
    public void registerCustomer(@RequestBody Customer newCustomer){
        customerService.register(newCustomer);
    }

    @PutMapping
    public void updateCustomer(@RequestParam(required = false) Integer newPin) {
        Customer target = getCurrentLoggedInUser();
        customerService.updateCustomer(target.getId(), newPin);
    }

    @DeleteMapping
    public void deleteCustomer(){
        Customer target = getCurrentLoggedInUser();
        customerService.deleteCustomer(target.getId());
    }
}
