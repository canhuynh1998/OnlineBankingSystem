package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public void registerCustomer(@RequestBody Customer newCustomer){
        customerService.addNewCustomer(newCustomer);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable Long customerId,
                                @RequestParam(required = false) Integer newPin) {
        customerService.updateCustomer(customerId, newPin);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
    }
}
