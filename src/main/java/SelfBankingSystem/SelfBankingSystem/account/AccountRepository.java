package SelfBankingSystem.SelfBankingSystem.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);

    Optional<Account> findByNumber(Long accountNumber);
}
