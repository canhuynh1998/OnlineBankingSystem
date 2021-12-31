package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfig{

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repo){
        return args -> {
            Customer can = new Customer("Can", 'M', 24, 306);
            repo.save(can);
        };
    }
}
