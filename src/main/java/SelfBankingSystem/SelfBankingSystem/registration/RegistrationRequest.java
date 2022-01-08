package SelfBankingSystem.SelfBankingSystem.registration;

import SelfBankingSystem.SelfBankingSystem.customer.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String name;
    private Character sex;
    private LocalDate dob;
    private String userName;
    private String password;
    private Integer pin;

}
