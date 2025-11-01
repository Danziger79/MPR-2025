package service;

import model.CompanyStatistics;
import model.Employee;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceAnalyticsTest {

    private EmployeeService service;

    @BeforeEach
    public void setUp() {
        service = new EmployeeService();
        service.addEmployee(new Employee("Jan Kowalski", "jan@tech.pl", "TechCorp", Position.PREZES, 27000));
        service.addEmployee(new Employee("Anna Nowak", "anna@tech.pl", "TechCorp", Position.PROGRAMISTA, 7000)); // below base 8000
        service.addEmployee(new Employee("Bartek K", "bartek@dev.pl", "DevHouse", Position.PROGRAMISTA, 9000)); // above base
        service.addEmployee(new Employee("Zosia Z", "zosia@dev.pl", "DevHouse", Position.PROGRAMISTA, 9000)); // tie top
    }

    @Test
    public void validateSalaryConsistency_shouldReturnThoseBelowBase() {
        // Act
        List<Employee> inconsistent = service.validateSalaryConsistency();

        // Assert
        assertEquals(1, inconsistent.size());
        assertEquals("Anna Nowak", inconsistent.get(0).getName());
    }

    @Test
    public void getCompanyStatistics_shouldReturnCorrectStats() {
        // Act
        Map<String, CompanyStatistics> stats = service.getCompanyStatistics();

        // Assert TechCorp
        assertTrue(stats.containsKey("TechCorp"));
        CompanyStatistics techStats = stats.get("TechCorp");
        assertEquals(2, techStats.getEmployeeCount());
        double expectedTechAvg = (27000 + 7000) / 2.0;
        assertEquals(expectedTechAvg, techStats.getAverageSalary(), 0.0001);
        assertEquals("Jan Kowalski", techStats.getTopEarnerFullName());

        // Assert DevHouse
        CompanyStatistics devStats = stats.get("DevHouse");
        assertEquals(2, devStats.getEmployeeCount());
        double expectedDevAvg = (9000 + 9000) / 2.0;
        assertEquals(expectedDevAvg, devStats.getAverageSalary(), 0.0001);
        // top earner could be either 'Bartek K' or 'Zosia Z' since equal salaries -> check one of them
        assertTrue(devStats.getTopEarnerFullName().equals("Bartek K") || devStats.getTopEarnerFullName().equals("Zosia Z"));
    }

    @Test
    public void edgeCase_singleEmployeeStatistics() {
        EmployeeService single = new EmployeeService();
        single.addEmployee(new Employee("Solo", "solo@x.pl", "SoloCorp", Position.MANAGER, 12000));

        Map<String, CompanyStatistics> stats = single.getCompanyStatistics();
        assertTrue(stats.containsKey("SoloCorp"));
        CompanyStatistics cs = stats.get("SoloCorp");
        assertEquals(1, cs.getEmployeeCount());
        assertEquals(12000, cs.getAverageSalary(), 0.0001);
        assertEquals("Solo", cs.getTopEarnerFullName());
    }
}
