package SelfBankingSystem.SelfBankingSystem.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class CustomerConfig{

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repo){
        return args -> {
            Customer can = new Customer("Can", 'M', LocalDate.of(1998, 6, 3), 306);
            repo.save(can);
        };
    }
}
