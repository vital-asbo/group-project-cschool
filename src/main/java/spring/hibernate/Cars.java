package spring.hibernate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Cars")
@Data
@AllArgsConstructor
public class Cars implements HibernateEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "EMPLOYEE_ID", nullable = false, referencedColumnName="ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    public Employees employees;

    @Column(name = "Name")
    private String name;

    @Column(name = "Model")
    private String model;

    @Column(name = "RegistrationDate")
    @ToString.Exclude
    private Date registrationDate;

    public Cars() {}
}
