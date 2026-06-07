package lab8.examples;

import lab8.common.Lab8Data;
import lab8.common.Lab8Files;
import lab8.common.SimpleXlsxWorkbook;

import java.nio.file.Path;
import java.util.List;

public class Example8ExcelRead {
    public static void main(String[] args) throws Exception {
        Path file = Lab8Files.demoDirectory("example8-excel-read").resolve("products.xlsx");
        SimpleXlsxWorkbook.writeSheet(file, "Products", Lab8Data.sampleProducts());

        for (List<String> row : SimpleXlsxWorkbook.readSheet(file, "Products")) {
            System.out.println(row);
        }
    }
}
