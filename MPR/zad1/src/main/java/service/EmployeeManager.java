package service;

import model.Employee;
import model.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeManager {

    private final List<Employee> employees = new ArrayList<>();

    // Dodawanie pracownika (sprawdza, czy email już istnieje)
    public boolean addEmployee(Employee employee) {
        for (Employee e : employees) {
            if (e.getEmail().equalsIgnoreCase(employee.getEmail())) {
                System.out.println("Pracownik o tym adresie email już istnieje!");
                return false;
            }
        }
        employees.add(employee);
        return true;
    }

    // Wyświetlanie wszystkich pracowników
    public void showAllEmployees() {
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    // Wyszukiwanie po firmie
    public List<Employee> findByCompany(String companyName) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getCompanyName().equalsIgnoreCase(companyName)) {
                result.add(e);
            }
        }
        return result;
    }

    // Sortowanie po imieniu (alfabetycznie)
    public List<Employee> sortByName() {
        // Tworzymy kopię listy, żeby nie zmieniać oryginału
        List<Employee> sortedList = new ArrayList<>(employees);
        for (int i = 0; i < sortedList.size() - 1; i++) {
            for (int j = 0; j < sortedList.size() - 1 - i; j++) {
                if (sortedList.get(j).getName().compareToIgnoreCase(sortedList.get(j + 1).getName()) > 0) {
                    Employee temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }
        return sortedList;
    }

    // Grupowanie po stanowisku
    public Map<Position, List<Employee>> groupByPosition() {
        Map<Position, List<Employee>> grouped = new HashMap<>();
        for (Employee e : employees) {
            Position pos = e.getPosition();
            if (!grouped.containsKey(pos)) {
                grouped.put(pos, new ArrayList<>());
            }
            grouped.get(pos).add(e);
        }
        return grouped;
    }

    // Liczba pracowników na danym stanowisku
    public Map<Position, Integer> countByPosition() {
        Map<Position, Integer> counts = new HashMap<>();
        for (Employee e : employees) {
            Position pos = e.getPosition();
            if (!counts.containsKey(pos)) {
                counts.put(pos, 0);
            }
            counts.put(pos, counts.get(pos) + 1);
        }
        return counts;
    }

    // Średnie wynagrodzenie
    public double averageSalary() {
        if (employees.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Employee e : employees) {
            sum += e.getSalary();
        }
        return sum / employees.size();
    }

    // Pracownik z najwyższym wynagrodzeniem
    public Employee findHighestSalary() {
        if (employees.isEmpty()) return null;
        Employee highest = employees.get(0);
        for (Employee e : employees) {
            if (e.getSalary() > highest.getSalary()) {
                highest = e;
            }
        }
        return highest;
    }
}
