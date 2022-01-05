package SelfBankingSystem.SelfBankingSystem.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerid);

    Optional<Account> findByNumber(Long accountNumber);

    Optional<Account> findByType(Integer type);

    Optional<Account> findByCustomerIdAndType(Long customerid, Integer type);


}
