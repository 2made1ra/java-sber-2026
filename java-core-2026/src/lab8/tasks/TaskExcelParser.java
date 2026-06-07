package lab8.tasks;

import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.SimpleXlsxWorkbook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class TaskExcelParser {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("task-excel-parser").resolve("products.xlsx");
        SimpleXlsxWorkbook.writeSheet(file, "Products", Lab8Data.sampleProducts());
        System.out.println("Excel file prepared: " + file.toAbsolutePath());

        try {
            for (List<String> row : SimpleXlsxWorkbook.readSheet(file, "Products")) {
                System.out.println(row);
            }
        } catch (IOException exception) {
            System.out.println("Read error: " + exception.getMessage());
            System.out.println("Recommendation: check that the file is XLSX and contains sheet 'Products'.");
        }

        try {
            SimpleXlsxWorkbook.readSheet(file, "MissingSheet");
        } catch (IOException exception) {
            System.out.println("Expected error handled: " + exception.getMessage());
        }
    }
}
