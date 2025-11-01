import model.Employee;
import model.Position;
import service.EmployeeManager;

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();

        manager.addEmployee(new Employee("Jan Kowalski", "jan@techcorp.pl", "TechCorp", Position.PREZES, 27000));
        manager.addEmployee(new Employee("Anna Nowak", "anna@techcorp.pl", "TechCorp", Position.PROGRAMISTA, 8500));
        manager.addEmployee(new Employee("Piotr Wiśniewski", "piotr@devhouse.pl", "DevHouse", Position.MANAGER, 12500));
        manager.addEmployee(new Employee("Ewa Zielińska", "ewa@techcorp.pl", "TechCorp", Position.STAZYSTA, 3200));

        System.out.println("Wszyscy pracownicy:");
        manager.showAllEmployees();

        System.out.println("\nPracownicy z TechCorp:");
        for (Employee e : manager.findByCompany("TechCorp")) {
            System.out.println(e);
        }

        System.out.println("\nPosortowani alfabetycznie:");
        for (Employee e : manager.sortByName()) {
            System.out.println(e);
        }

        System.out.println("\nGrupowanie po stanowisku:");
        for (var entry : manager.groupByPosition().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().size() + " -> " + entry.getValue());
        }

        System.out.println("\nLiczba pracowników na stanowiskach:");
        for (var entry : manager.countByPosition().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nŚrednie wynagrodzenie: " + manager.averageSalary());

        System.out.println("Najlepiej zarabiający: ");
        Employee best = manager.findHighestSalary();
        if (best != null) {
            System.out.println(best);
        } else {
            System.out.println("Brak danych");
        }
    }
}
