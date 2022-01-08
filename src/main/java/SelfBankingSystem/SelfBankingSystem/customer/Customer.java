package SelfBankingSystem.SelfBankingSystem.customer;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(
        name="customer",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"Username"}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_sequence"
    )
    private Long id;

    @Column(
            name="Name",
            nullable = false
    )
    private String name;

    @Column(
            name="Sex",
            nullable = false
    )
    private Character sex;

    @Column(
            name="Birthday",
            nullable = false
    )
    private LocalDate dob;

    @Transient
    private Integer age ;

    @Enumerated(EnumType.STRING)
    @Column(
            name="Role",
            nullable = false
    )
    private UserRole userRole;

    @Column(
            name="Username",
            nullable = false
    )
    private String userName;
    @Column(
            name="Password",
            nullable = false
    )
    private String password;

    @Column(
            name="Pin",
            nullable = false
    )
    private Integer pin;

    public Customer(String name,
                    Character sex,
                    LocalDate dob,
                    UserRole userRole,
                    String userName,
                    String password,
                    Integer pin) {
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.userRole = userRole;
        this.userName = userName;
        this.password = password;
        this.pin = pin;
    }

    public Integer getAge(){
        return Period.between(dob,LocalDate.now()).getYears();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
