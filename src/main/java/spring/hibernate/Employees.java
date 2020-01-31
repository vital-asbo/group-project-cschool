package spring.hibernate;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Employees", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Employees implements HibernateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "LastName")
    @Getter@Setter
    @NonNull
    private String lastName;

    @Column(name = "FirstName")
    @Getter@Setter
    @NonNull
    private String firstName;

    @Column(name = "Address")
    @Getter@Setter
    @NonNull
    private String address;

    @Column(name = "City")
    @Getter@Setter
    @NonNull
    private String city;

    @Column(name = "Salary")
    @Getter@Setter
    @NonNull
    private int salary;

    @Column(name = "Age")
    @Getter@Setter
    @NonNull
    private int age;

    @Column(name = "StartJobDate")
    @Getter@Setter
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date startJobDate;

    @Column(name = "Benefit")
    @Getter@Setter
    @NonNull
    private int benefit;

    @Column(name = "Email")
    @Getter@Setter
    private String email;

    @OneToMany(mappedBy = "employees", orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Getter@Setter
    @EqualsAndHashCode.Exclude
    private Set<Cars> cars;

    @OneToMany(mappedBy = "employees", orphanRemoval = true, fetch = FetchType.EAGER)
    @ToString.Exclude
    @Getter@Setter
    @EqualsAndHashCode.Exclude
    private Set<Phones> phones;

//    @OneToMany(mappedBy = "employees", orphanRemoval = true, fetch = FetchType.EAGER)


//    @ManyToMany(mappedBy = "employees")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<Printer> printers;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="Employees_Printer", joinColumns={@JoinColumn(referencedColumnName="ID")}
            , inverseJoinColumns={@JoinColumn(referencedColumnName="ID")})
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    @Getter@Setter
    @EqualsAndHashCode.Exclude
    private Set<Printer> printers = new HashSet<>();



    public Employees(){}

}
