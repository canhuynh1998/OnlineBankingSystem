package SelfBankingSystem.SelfBankingSystem.account;

import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import javax.persistence.*;


@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = { "Number", "Id" }
                )
        }
        )
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
    private Long id;

    @Column(
            name="Balance",
            nullable = false
    )
    private Long balance;

    @Column(
            name="Type",
            nullable = false
    )
    private Integer type = 1; // 0: checking, 1: saving

    @Column(
            name="Status",
            nullable = false
    )
    private Integer active = 1;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;


    public Account(){}

    public Account(Long balance, Integer type, Customer customer) {
        this.balance = balance;
        this.type = type;
        this.customer = customer;
    }

    public Long getNumber() {
        return number;
    }

    public Long getId() {
        return id;
    }

    public Long getBalance() {
        return balance;
    }

    public Integer getType() {
        return type;
    }

    public Integer getActive() {
        return active;
    }

    public Customer getCustomer() {
        return customer;
    }


    public void setNumber(Long number) {
        this.number = number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setActive(Integer active) {
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
