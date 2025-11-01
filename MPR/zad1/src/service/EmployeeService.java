package service;

import model.CompanyStatistics;
import model.Employee;
import model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public boolean addEmployee(Employee employee) {
        for (Employee e : employees) {
            if (e.getEmail().equalsIgnoreCase(employee.getEmail())) {
                return false;
            }
        }
        employees.add(employee);
        return true;
    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees);
    }

    // find by company
    public List<Employee> findByCompany(String companyName) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getCompanyName().equalsIgnoreCase(companyName)) {
                result.add(e);
            }
        }
        return result;
    }

    // average salary overall
    public double averageSalary() {
        if (employees.isEmpty()) return 0.0;
        double sum = 0.0;
        for (Employee e : employees) {
            sum += e.getSalary();
        }
        return sum / employees.size();
    }

    // highest salary
    public Employee findHighestSalary() {
        if (employees.isEmpty()) return null;
        Employee best = employees.get(0);
        for (Employee e : employees) {
            if (e.getSalary() > best.getSalary()) {
                best = e;
            }
        }
        return best;
    }

    // validate salary consistency: salary < base salary for position
    public List<Employee> validateSalaryConsistency() {
        List<Employee> inconsistent = new ArrayList<>();
        for (Employee e : employees) {
            Position pos = e.getPosition();
            if (pos != null && e.getSalary() < pos.getBaseSalary()) {
                inconsistent.add(e);
            }
        }
        return inconsistent;
    }

    // get company statistics: map companyName -> CompanyStatistics
    public Map<String, CompanyStatistics> getCompanyStatistics() {
        Map<String, List<Employee>> groups = new HashMap<>();
        // group employees by company
        for (Employee e : employees) {
            String company = e.getCompanyName();
            if (!groups.containsKey(company)) {
                groups.put(company, new ArrayList<>());
            }
            groups.get(company).add(e);
        }

        Map<String, CompanyStatistics> stats = new HashMap<>();
        for (Map.Entry<String, List<Employee>> entry : groups.entrySet()) {
            String company = entry.getKey();
            List<Employee> list = entry.getValue();
            int count = list.size();
            double sum = 0.0;
            Employee top = null;
            for (Employee e : list) {
                sum += e.getSalary();
                if (top == null || e.getSalary() > top.getSalary()) {
                    top = e;
                }
            }
            double average = count == 0 ? 0.0 : sum / count;
            String topName = top == null ? "" : top.getName();
            CompanyStatistics cs = new CompanyStatistics(count, average, topName);
            stats.put(company, cs);
        }
        return stats;
    }
}
