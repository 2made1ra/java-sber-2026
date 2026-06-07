package lab8.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class SimpleXlsxWorkbook {
    private static final String SHEET_NS = "http://schemas.openxmlformats.org/spreadsheetml/2006/main";

    private SimpleXlsxWorkbook() {
    }

    public static void writeSheet(Path file, String sheetName, List<List<String>> rows) throws IOException {
        Lab8Files.createParentDirectories(file);

        ZipOutputStream zipOutputStream = new ZipOutputStream(java.nio.file.Files.newOutputStream(file));
        try {
            putEntry(zipOutputStream, "[Content_Types].xml", contentTypesXml());
            putEntry(zipOutputStream, "_rels/.rels", packageRelationshipsXml());
            putEntry(zipOutputStream, "xl/workbook.xml", workbookXml(sheetName));
            putEntry(zipOutputStream, "xl/_rels/workbook.xml.rels", workbookRelationshipsXml());
            putEntry(zipOutputStream, "xl/worksheets/sheet1.xml", worksheetXml(rows));
        } finally {
            zipOutputStream.close();
        }
    }

    public static List<List<String>> readSheet(Path file, String sheetName) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(file.toFile());
            try {
                String availableSheet = readWorkbookSheetName(zipFile);
                if (!availableSheet.equals(sheetName)) {
                    throw new IOException("Sheet '" + sheetName + "' was not found. Available sheet: '"
                            + availableSheet + "'. Check the sheet name or recreate the XLSX file.");
                }

                return readWorksheet(zipFile);
            } finally {
                zipFile.close();
            }
        } catch (IOException exception) {
            throw new IOException("Cannot read Excel file '" + file + "': " + exception.getMessage(), exception);
        }
    }

    private static String readWorkbookSheetName(ZipFile zipFile) throws IOException {
        Document document = parseXml(readEntry(zipFile, "xl/workbook.xml"));
        NodeList sheets = document.getElementsByTagNameNS(SHEET_NS, "sheet");

        if (sheets.getLength() == 0) {
            throw new IOException("workbook.xml does not contain sheets. Create the workbook again.");
        }

        return ((Element) sheets.item(0)).getAttribute("name");
    }

    private static List<List<String>> readWorksheet(ZipFile zipFile) throws IOException {
        Document document = parseXml(readEntry(zipFile, "xl/worksheets/sheet1.xml"));
        NodeList rowNodes = document.getElementsByTagNameNS(SHEET_NS, "row");
        List<List<String>> rows = new ArrayList<List<String>>();

        for (int rowIndex = 0; rowIndex < rowNodes.getLength(); rowIndex++) {
            Element rowElement = (Element) rowNodes.item(rowIndex);
            NodeList cellNodes = rowElement.getElementsByTagNameNS(SHEET_NS, "c");
            List<String> row = new ArrayList<String>();

            for (int cellIndex = 0; cellIndex < cellNodes.getLength(); cellIndex++) {
                row.add(readCellText((Element) cellNodes.item(cellIndex)));
            }

            rows.add(row);
        }

        return rows;
    }

    private static String readCellText(Element cell) {
        NodeList textNodes = cell.getElementsByTagNameNS(SHEET_NS, "t");
        if (textNodes.getLength() > 0) {
            return textNodes.item(0).getTextContent();
        }

        NodeList valueNodes = cell.getElementsByTagNameNS(SHEET_NS, "v");
        return valueNodes.getLength() > 0 ? valueNodes.item(0).getTextContent() : "";
    }

    private static byte[] readEntry(ZipFile zipFile, String name) throws IOException {
        ZipEntry entry = zipFile.getEntry(name);
        if (entry == null) {
            throw new IOException("missing required entry '" + name + "'. The file may have a wrong format.");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        java.io.InputStream inputStream = zipFile.getInputStream(entry);
        try {
            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
        } finally {
            inputStream.close();
        }

        return outputStream.toByteArray();
    }

    private static Document parseXml(byte[] bytes) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setNamespaceAware(true);
            return factory.newDocumentBuilder().parse(new ByteArrayInputStream(bytes));
        } catch (Exception exception) {
            throw new IOException("cannot parse XLSX XML: " + exception.getMessage(), exception);
        }
    }

    private static void putEntry(ZipOutputStream zipOutputStream, String name, String content) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(name));
        zipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        zipOutputStream.closeEntry();
    }

    private static String contentTypesXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
                + "<Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>"
                + "<Default Extension=\"xml\" ContentType=\"application/xml\"/>"
                + "<Override PartName=\"/xl/workbook.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml\"/>"
                + "<Override PartName=\"/xl/worksheets/sheet1.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml\"/>"
                + "</Types>";
    }

    private static String packageRelationshipsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"xl/workbook.xml\"/>"
                + "</Relationships>";
    }

    private static String workbookRelationshipsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet\" Target=\"worksheets/sheet1.xml\"/>"
                + "</Relationships>";
    }

    private static String workbookXml(String sheetName) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<workbook xmlns=\"" + SHEET_NS + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">"
                + "<sheets><sheet name=\"" + escapeXml(sheetName) + "\" sheetId=\"1\" r:id=\"rId1\"/></sheets>"
                + "</workbook>";
    }

    private static String worksheetXml(List<List<String>> rows) {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        builder.append("<worksheet xmlns=\"").append(SHEET_NS).append("\"><sheetData>");

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
            builder.append("<row r=\"").append(rowIndex + 1).append("\">");
            List<String> row = rows.get(rowIndex);

            for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
                String value = row.get(cellIndex);
                String reference = columnName(cellIndex) + (rowIndex + 1);

                if (value.matches("-?\\d+(\\.\\d+)?")) {
                    builder.append("<c r=\"").append(reference).append("\"><v>")
                            .append(value)
                            .append("</v></c>");
                } else {
                    builder.append("<c r=\"").append(reference).append("\" t=\"inlineStr\"><is><t>")
                            .append(escapeXml(value))
                            .append("</t></is></c>");
                }
            }

            builder.append("</row>");
        }

        builder.append("</sheetData></worksheet>");
        return builder.toString();
    }

    private static String columnName(int index) {
        StringBuilder builder = new StringBuilder();
        int number = index + 1;

        while (number > 0) {
            number--;
            builder.insert(0, (char) ('A' + number % 26));
            number /= 26;
        }

        return builder.toString();
    }

    private static String escapeXml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
