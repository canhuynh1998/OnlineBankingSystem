package SelfBankingSystem.SelfBankingSystem;

import SelfBankingSystem.SelfBankingSystem.account.Account;
import SelfBankingSystem.SelfBankingSystem.account.AccountRepository;
import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import SelfBankingSystem.SelfBankingSystem.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class SelfBankingSystemApplication implements CommandLineRunner {

	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(SelfBankingSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


//		Account canaccount = new Account(100L,100000L,1);
//		Customer can = new Customer("Can", 'M', LocalDate.of(1998, 6, 3), 306);
//		customerRepo.save(can);
//		canaccount.setCustomer(can);
//
//
//		accountRepo.save(canaccount);

		}

}
