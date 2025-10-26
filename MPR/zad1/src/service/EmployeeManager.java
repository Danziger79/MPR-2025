package service;

import model.Employee;
import model.Position;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManager {
    private final List<Employee> employees = new ArrayList<>();


    public boolean addEmployee(Employee employee) {
        if (employees.stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(employee.getEmail()))) {
            System.out.println("Pracownik o tym adresie email już istnieje!");
            return false;
        }
        employees.add(employee);
        return true;
    }

    // Wyswietlanie  pracowników
    public void showAllEmployees() {
        employees.forEach(System.out::println);
    }

    // Wyszukiwanie po firmie
    public List<Employee> findByCompany(String companyName) {
        return employees.stream()
                .filter(e -> e.getCompanyName().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    // sortowanie po nazwisku
    public List<Employee> sortByName() {
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.toList());
    }

    // Grupowanie po stanowisku
    public Map<Position, List<Employee>> groupByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition));
    }

    //zlicznie liczby pracowników na stanowisku
    public Map<Position, Long> countByPosition() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));
    }

    // srednie wynagrodzenie
    public double averageSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    // Pracownik z najwyzszym wynagrodzeniem
    public Optional<Employee> findHighestSalary() {
        return employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
    }
}
