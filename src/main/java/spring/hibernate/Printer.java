package spring.hibernate;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Printer",uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID")})
@ToString
@RequiredArgsConstructor
public class Printer implements HibernateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "model")
    @Getter @Setter
    @NonNull
    private String model;


    @Column(name = "producer")
    @Getter @Setter
    @NonNull
    private String producer;


//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "EMPLOYEES", joinColumns={@JoinColumn(referencedColumnName = "ID")}, inverseJoinColumns={@JoinColumn(referencedColumnName="ID")})
//    @Getter @Setter
//    @NonNull
//    public Employees employees;

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "EMPLOYEE_ID", nullable = false, referencedColumnName = "ID")
//    @Getter @Setter
//    @NonNull
//    public Employees employees;

    @ManyToMany(mappedBy="printers")
    @Getter @Setter
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<Employees> employees = new HashSet<>();



    public Printer(){}

}
