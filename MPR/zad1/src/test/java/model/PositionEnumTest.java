package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionEnumTest {

    @Test
    public void eachPosition_shouldHaveExpectedBaseSalaryAndHierarchy() {
        // PREZES
        assertEquals(25000.0, Position.PREZES.getBaseSalary(), 0.001);
        assertEquals(1, Position.PREZES.getHierarchyLevel());

        // WICEPREZES
        assertEquals(18000.0, Position.WICEPREZES.getBaseSalary(), 0.001);
        assertEquals(2, Position.WICEPREZES.getHierarchyLevel());

        // MANAGER
        assertEquals(12000.0, Position.MANAGER.getBaseSalary(), 0.001);
        assertEquals(3, Position.MANAGER.getHierarchyLevel());

        // PROGRAMISTA
        assertEquals(8000.0, Position.PROGRAMISTA.getBaseSalary(), 0.001);
        assertEquals(4, Position.PROGRAMISTA.getHierarchyLevel());

        // STAZYSTA
        assertEquals(3000.0, Position.STAZYSTA.getBaseSalary(), 0.001);
        assertEquals(5, Position.STAZYSTA.getHierarchyLevel());
    }
}
