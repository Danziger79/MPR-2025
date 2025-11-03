package service;

import model.Employee;
import model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeManagerTest {

    private EmployeeManager manager;

    @BeforeEach
    public void setUp() {
        manager = new EmployeeManager();

        manager.addEmployee(new Employee("Jan Kowalski", "jan@techcorp.pl", "TechCorp", Position.PREZES, 27000));
        manager.addEmployee(new Employee("Anna Nowak", "anna@techcorp.pl", "TechCorp", Position.PROGRAMISTA, 8500));
        manager.addEmployee(new Employee("Piotr Wiśniewski", "piotr@devhouse.pl", "DevHouse", Position.MANAGER, 12500));
        manager.addEmployee(new Employee("Ewa Zielińska", "ewa@techcorp.pl", "TechCorp", Position.STAZYSTA, 3200));
    }

    @Test
    public void addEmployee_shouldRejectDuplicateEmail() {
        // Arrange
        Employee duplicate = new Employee("Imię Nazwisko", "jan@techcorp.pl", "Other", Position.PROGRAMISTA, 8000);

        // Act
        boolean added = manager.addEmployee(duplicate);

        // Assert
        assertFalse(added, "Adding employee with duplicate email should return false");
    }

    @Test
    public void findByCompany_shouldReturnOnlyCompanyEmployees() {
        // Act
        List<Employee> techcorp = manager.findByCompany("TechCorp");

        // Assert
        assertEquals(3, techcorp.size(), "TechCorp should have 3 employees in test set");
        for (Employee e : techcorp) {
            assertEquals("TechCorp", e.getCompanyName());
        }
    }

    @Test
    public void sortByName_shouldReturnEmployeesSortedAlphabetically() {
        // Act
        List<Employee> sorted = manager.sortByName();

        // Assert - names in alphabetical order by full name:
        assertEquals("Anna Nowak", sorted.get(0).getName());
        assertEquals("Ewa Zielińska", sorted.get(1).getName());
        assertEquals("Jan Kowalski", sorted.get(2).getName());
        assertEquals("Piotr Wiśniewski", sorted.get(3).getName());
    }

    @Test
    public void groupByPosition_and_countByPosition_shouldWork() {
        // Act
        Map<Position, List<Employee>> grouped = manager.groupByPosition();
        Map<Position, Integer> counts = manager.countByPosition(); // ← poprawka: Integer

        // Assert basic counts
        assertTrue(grouped.containsKey(Position.PREZES));
        assertEquals(1, grouped.get(Position.PREZES).size());

        assertEquals(1, counts.get(Position.PREZES).intValue());
        assertEquals(1, counts.get(Position.PROGRAMISTA).intValue());
        assertEquals(1, counts.get(Position.MANAGER).intValue());
        assertEquals(1, counts.get(Position.STAZYSTA).intValue());
    }

    @Test
    public void averageSalary_and_findHighestSalary_shouldBeCorrect() {
        // Act
        double avg = manager.averageSalary();
        Employee best = manager.findHighestSalary();

        // compute expected average:
        double expected = (27000 + 8500 + 12500 + 3200) / 4.0;

        // Assert
        assertEquals(expected, avg, 0.0001);
        assertNotNull(best);
        assertEquals("Jan Kowalski", best.getName());
        assertEquals(27000, best.getSalary(), 0.0001);
    }

    @Test
    public void edgeCases_emptyManager() {
        // Arrange
        EmployeeManager empty = new EmployeeManager();

        // Act & Assert
        assertEquals(0, empty.sortByName().size());
        assertEquals(0, empty.findByCompany("Nope").size());
        assertTrue(empty.groupByPosition().isEmpty());
        assertTrue(empty.countByPosition().isEmpty());
        assertEquals(0.0, empty.averageSalary(), 0.0001);
        assertNull(empty.findHighestSalary(), "Expected null when there are no employees");
    }
}
