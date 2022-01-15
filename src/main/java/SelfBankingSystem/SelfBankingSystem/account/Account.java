package SelfBankingSystem.SelfBankingSystem.account;
import SelfBankingSystem.SelfBankingSystem.customer.Customer;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;

@Entity
@Table(
        name="account",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = { "Number"}
                )
        }
        )
@Data
@NoArgsConstructor
public class Account {
    @Id
    @SequenceGenerator(
            name="account_sequence",
            sequenceName ="account_sequence",
            allocationSize = 1,
            initialValue = 100000000
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
            name="Balance",
            nullable = false
    )
    private Long balance;

    @Column(
            name="Type",
            nullable = false
    )
    private Integer type ; // 0: checking, 1: saving

    @Column(
            name="Status",
            nullable = false
    )
    private Integer active = 1; // 1: active -1: inactive

    @ManyToOne()
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @JoinColumn(
            name = "customer",
            referencedColumnName = "id"
    )
    private Customer customer;

    public Account(Long balance, Integer type) {
        this.balance = balance;
        this.type = type;
    }

}
