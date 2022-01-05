package SelfBankingSystem.SelfBankingSystem.customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(
        name="customer"
)
@Data
@NoArgsConstructor
public class Customer {
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

    @Column(
            name="Pin",
            nullable = false
    )
    private Integer pin;


    public Customer(String name, Character sex, LocalDate dob, Integer pin) {
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.pin = pin;
    }

    public Integer getAge(){
        return Period.between(dob,LocalDate.now()).getYears();
    }
}
