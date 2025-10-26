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

        System.out.println("Wszyscy pracownicy");
        manager.showAllEmployees();

        System.out.println("\nPracownicy z TechCorp");
        manager.findByCompany("TechCorp").forEach(System.out::println);

        System.out.println("\nPosortowani alfabetycznie");
        manager.sortByName().forEach(System.out::println);

        System.out.println("\nGrupowanie po stanowisku");
        manager.groupByPosition().forEach((pos, list) -> {
            System.out.println(pos + ": " + list.size() + " -> " + list);
        });

        System.out.println("\nLiczba pracowników na stanowiskach");
        manager.countByPosition().forEach((pos, count) ->
                System.out.println(pos + ": " + count));

        System.out.println("\nŚrednie wynagrodzenie: " + manager.averageSalary());
        System.out.println("Najlepiej zarabiający: " +
                manager.findHighestSalary().map(Employee::toString).orElse("Brak danych"));
    }
}
