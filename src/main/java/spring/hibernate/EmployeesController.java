package spring.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

@Controller
public class EmployeesController {

    private List<Employees> list;
    private HibernateDao hibernateDao;
    private List<Printer> printersList;

    public EmployeesController() {
        try {
              hibernateDao = new HibernateDao();
//            DataSource.supplyDatabase();
            list = hibernateDao.get(Employees.class);
            printersList = hibernateDao.get(Printer.class);
        } catch (NullPointerException ex) {
            System.out.println("Brak połączenia z bazą danych");
            ex.getMessage();
            list = DataSource.getEmployeeList();
        }
    }

    @RequestMapping("/")
    public String indexGet() {
        return "employees/index_employees";
    }

    @RequestMapping(value = "/employees_form", method = RequestMethod.GET)
    public String showForm(Model model) {
        Employees employees = new Employees();
        employees.setStartJobDate(new Date());
//        Printer printer = new Printer();

        model.addAttribute("employees", employees);

        return "employees/employees_form";
    }

    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "employees") Employees employees) {
        if (employees.getId() == 0) {
            System.out.println("Adding a new emp");
            employees.setId(list.size() + 1);
            list.add(employees);
            addEmployeeInDB(employees);
        } else {
            updateEmployeeInList(employees);
            updateEmployeeInDB(employees);
        }

        return new ModelAndView("redirect:/employees_list");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "employees_id") String emp_id) {
        Employees employeesTemp = getEmployeesById(Integer.parseInt(emp_id));
        list.remove(employeesTemp);
        deleteEmployeeInDB(employeesTemp);
        return new ModelAndView("redirect:/employees_list");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "employees_id") String emp_id) {
        Employees employeesTemp = getEmployeesById(Integer.parseInt(emp_id));
        updateEmployeeInDB(employeesTemp);
        return new ModelAndView("employees/employees_form", "employees", employeesTemp);
    }

    @RequestMapping("/employees_list")
    public ModelAndView showEmployeesList(Model model) {
        System.out.println(list.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", list);
        modelAndView.addObject("printersList", printersList);
        modelAndView.setViewName("employees/employees_list");
        return modelAndView;
    }

    @RequestMapping("/printers_list")
    public ModelAndView showPrintersList(Model model) {
        System.out.println(list.toString());
        return new ModelAndView("employees/printers_list", "list", printersList);
    }

    private Employees getEmployeesById(@RequestParam int id) {
        System.out.println(id);
        return list.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void updateEmployeeInList(Employees employees) {
        Employees employeesTemp = getEmployeesById(employees.getId());
        employeesTemp.setFirstName(employees.getFirstName());
        employeesTemp.setLastName(employees.getLastName());
        employeesTemp.setAddress(employees.getAddress());
        employeesTemp.setCity(employees.getCity());
        employeesTemp.setSalary(employees.getSalary());
        employeesTemp.setAge(employees.getAge());
        employeesTemp.setStartJobDate(employees.getStartJobDate());
        employeesTemp.setBenefit(employees.getBenefit());
        employeesTemp.setCars(employees.getCars());
        employeesTemp.setPhones(employees.getPhones());
    }

    private void updateEmployeeInDB(Employees employees) {
        if (DataSource.isDataBaseConnection) {
            hibernateDao.update(employees);
        }
    }

    private void addEmployeeInDB(Employees employees) {
        if (DataSource.isDataBaseConnection) {
            hibernateDao.save(employees);
        }
    }

    private void deleteEmployeeInDB(Employees employees) {
        if (DataSource.isDataBaseConnection) {
            hibernateDao.delete(employees);
        }
    }
}