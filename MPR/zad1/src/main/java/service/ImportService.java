package service;

import model.Employee;
import model.ImportSummary;
import model.Position;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// ImportService używa EmployeeService do dodawania pracowników
public class ImportService {

    private final EmployeeService employeeService;

    public ImportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Import z CSV. Struktura CSV: firstName,lastName,email,company,position,salary
     * Pomija nagłówek i puste linie. Zwraca ImportSummary z liczbą zaimportowanych i listą błędów.
     */
    public ImportSummary importFromCsv(String path) {
        List<String> errors = new ArrayList<>();
        int imported = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int lineNumber = 0;
            // odczyt linii po linii
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (lineNumber == 1) {
                    // pomijamy nagłówek
                    continue;
                }
                if (line.isEmpty()) {
                    // pomijamy puste linie
                    continue;
                }
                String[] parts = line.split(",", -1);
                if (parts.length < 6) {
                    errors.add("Line " + lineNumber + ": zbyt mało pól");
                    continue;
                }
                String firstName = parts[0].trim();
                String lastName = parts[1].trim();
                String email = parts[2].trim();
                String company = parts[3].trim();
                String positionStr = parts[4].trim();
                String salaryStr = parts[5].trim();

                // Walidacja pól
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || company.isEmpty()) {
                    errors.add("Line " + lineNumber + ": brak wymaganych danych (imię/nazwisko/email/firma)");
                    continue;
                }

                Position position;
                try {
                    position = Position.valueOf(positionStr.toUpperCase());
                } catch (IllegalArgumentException ex) {
                    errors.add("Line " + lineNumber + ": nieznane stanowisko '" + positionStr + "'");
                    continue;
                }

                double salary;
                try {
                    salary = Double.parseDouble(salaryStr);
                    if (salary <= 0) {
                        errors.add("Line " + lineNumber + ": wynagrodzenie musi być dodatnie");
                        continue;
                    }
                } catch (NumberFormatException ex) {
                    errors.add("Line " + lineNumber + ": błędne wynagrodzenie '" + salaryStr + "'");
                    continue;
                }

                // Stworzenie Employee (łączymy imię i nazwisko jako jedno pole name)
                String fullName = firstName + " " + lastName;
                Employee emp = new Employee(fullName, email, company, position, salary);
                boolean added = employeeService.addEmployee(emp);
                if (added) {
                    imported++;
                } else {
                    errors.add("Line " + lineNumber + ": pracownik o emailu '" + email + "' już istnieje");
                }
            }
        } catch (IOException e) {
            errors.add("IOException podczas czytania pliku: " + e.getMessage());
        }
        return new ImportSummary(imported, errors);
    }
}
