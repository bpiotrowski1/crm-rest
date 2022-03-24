package pl.bpiotrowski.crmrest.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String firstField;

    @Column
    private String secondField;

    @Column
    private String thirdField;

    @Column
    private String fourthField;

    @Column
    private String textArea;

}
