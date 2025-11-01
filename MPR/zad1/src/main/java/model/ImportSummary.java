package model;

import java.util.ArrayList;
import java.util.List;

public class ImportSummary {
    private final int importedCount;
    private final List<String> errors;

    public ImportSummary(int importedCount, List<String> errors) {
        this.importedCount = importedCount;
        this.errors = new ArrayList<>(errors);
    }

    public int getImportedCount() {
        return importedCount;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Zaimportowano: ").append(importedCount).append("\n");
        if (errors.isEmpty()) {
            sb.append("Brak błędów.");
        } else {
            sb.append("Błędy:\n");
            for (String e : errors) {
                sb.append(e).append("\n");
            }
        }
        return sb.toString();
    }
}
