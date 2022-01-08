package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.getCustomers();
    }


    @GetMapping(path = "{customerId}")
    public Customer getCustomer(@PathVariable Long customerId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer target = (Customer) authentication.getPrincipal();
        if(target.getId() != customerId){
            throw new SecurityException("Invalid Request!");
        }
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public void registerCustomer(@RequestBody Customer newCustomer){
        customerService.register(newCustomer);
    }

    @PutMapping(path = "{customerId}")

    public void updateCustomer(@PathVariable Long customerId,
                                @RequestParam(required = false) Integer newPin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer target = (Customer) authentication.getPrincipal();
        if(target.getId() != customerId){
            throw new IllegalStateException("Invalid Request!");
        }
        customerService.updateCustomer(customerId, newPin);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer target = (Customer) authentication.getPrincipal();
        if(target.getId() != customerId){
            throw new IllegalStateException("Invalid Request!");
        }
        customerService.deleteCustomer(customerId);
    }
}
