package SelfBankingSystem.SelfBankingSystem.customer;

import SelfBankingSystem.SelfBankingSystem.account.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Entity
@Table(
        name="customer"
)
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
    private Integer age;

    @Column(
            name="Pin",
            nullable = false
    )
    private Integer pin;

    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    private Set<Account> accounts;

    public Customer() {}

    public Customer(String name, Character sex, LocalDate dob, Integer pin) {
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.pin = pin;
    }

    /********** Setters **********/
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    /********** Getters **********/
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Character getSex() {
        return sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public Integer getPin() {
        return pin;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", age=" + age +
                ", pin=" + pin +
                '}';
    }
}
