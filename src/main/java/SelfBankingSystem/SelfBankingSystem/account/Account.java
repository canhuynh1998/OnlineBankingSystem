package SelfBankingSystem.SelfBankingSystem.account;

import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
public class Account {

    @Id
    @SequenceGenerator(
            name="account_sequence",
            sequenceName ="account_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    @Column(
            name="Number",
            nullable = false
    )
    private Long number;

    @Column(
            name="Id",
            nullable = false
    )
    private Integer id;

    @Column(
            name="Balance",
            nullable = false
    )
    private Long balance;

    @Column(
            name="Type",
            nullable = false
    )
    private Integer type; // 0: checking, 1: saving

    @Column(
            name="Status",
            nullable = false
    )
    private Boolean active;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public Account(){}

    public Account(Long balance, Integer type, Boolean active, Customer customer) {
        this.balance = balance;
        this.type = type;
        this.active = active;
        this.customer = customer;
    }

    public Long getNumber() {
        return number;
    }

    public Integer getId() {
        return id;
    }

    public Long getBalance() {
        return balance;
    }

    public Integer getType() {
        return type;
    }

    public Boolean getActive() {
        return active;
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setNumber(Long number) {
        this.number = number;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", id=" + id +
                ", balance=" + balance +
                ", type=" + type +
                ", active=" + active +
                ", customer=" + customer +
                '}';
    }
}
