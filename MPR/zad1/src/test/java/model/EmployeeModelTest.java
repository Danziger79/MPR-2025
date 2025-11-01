package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeModelTest {

    @Test
    public void equalsAndHashCode_shouldBeBasedOnEmail() {
        // Arrange
        Employee e1 = new Employee("Jan Kowalski", "jan@x.pl", "X", Position.PROGRAMISTA, 8000);
        Employee e2 = new Employee("Jan Nowy", "jan@x.pl", "Y", Position.MANAGER, 12000);
        Employee e3 = new Employee("Inny", "inny@x.pl", "X", Position.PROGRAMISTA, 8000);

        // Act & Assert
        assertEquals(e1, e2, "Employees with same email should be equal");
        assertEquals(e1.hashCode(), e2.hashCode(), "hashCode must be equal for same email");
        assertNotEquals(e1, e3, "Different email -> not equal");
    }

    @Test
    public void toString_shouldContainNamePositionSalaryCompany() {
        // Arrange
        Employee e = new Employee("Anna Nowak", "anna@x.pl", "XCorp", Position.MANAGER, 12000);

        // Act
        String s = e.toString();

        // Assert
        assertTrue(s.contains("Anna Nowak"));
        assertTrue(s.contains("MANAGER"));
        assertTrue(s.contains("12000"));
        assertTrue(s.contains("XCorp"));
    }
}
