package spring.hibernate;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="Printer")
@Table(name = "Printer")
//@Table(name = "Printer",uniqueConstraints = {
//        @UniqueConstraint(columnNames = "ID")})
@ToString
@Data
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

//    @ManyToMany(mappedBy="printers")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter @Setter
    @EqualsAndHashCode.Exclude
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinTable(name="Printer_Employees", joinColumns= @JoinColumn(name="printer_id")
            , inverseJoinColumns=@JoinColumn(name="employee_id"))
    private List<Employees> employeeses;


    public List<Employees> getEmployees(){
        if (employeeses == null){
            employeeses=  new ArrayList<>();      }
        return employeeses;
    }

    public void setEmployees(List<Employees> employees)
    {
        this.employeeses= employeeses;
    }


    public Printer(){}

}
