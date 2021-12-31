package SelfBankingSystem.SelfBankingSystem.customer;

import javax.persistence.*;

@Entity
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
            name="Gender",
            nullable = false
    )
    private Character gender;

    @Column(
            name="Age",
            nullable = false
    )
    private Integer age;

    @Column(
            name="Pin",
            nullable = false
    )
    private Integer pin;

    public Customer() {}

    public Customer(String name, Character gender, Integer age, Integer pin) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", pin=" + pin +
                '}';
    }
}
