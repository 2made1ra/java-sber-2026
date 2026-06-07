package lab8.examples;

import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.SimpleXlsxWorkbook;

import java.nio.file.Path;

public class Example7ExcelCreate {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example7-excel-create").resolve("products.xlsx");
        SimpleXlsxWorkbook.writeSheet(file, "Products", Lab8Data.sampleProducts());
        System.out.println("Excel file created: " + file.toAbsolutePath());
    }
}
