package lab8.common;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class XmlBookLibrary {
    private XmlBookLibrary() {
    }

    public static void write(Path file, List<Book> books) throws Exception {
        Document document = createEmptyDocument();
        Element root = document.getDocumentElement();

        for (Book book : books) {
            appendBook(document, root, book);
        }

        save(document, file);
    }

    public static List<Book> read(Path file) throws Exception {
        if (!Files.exists(file)) {
            return new ArrayList<Book>();
        }

        Document document = readDocument(file);
        NodeList nodes = document.getElementsByTagName("book");
        List<Book> books = new ArrayList<Book>();

        for (int index = 0; index < nodes.getLength(); index++) {
            Node node = nodes.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                books.add(new Book(
                        text(element, "title"),
                        text(element, "author"),
                        Integer.parseInt(text(element, "year"))
                ));
            }
        }

        return books;
    }

    public static void addBook(Path file, Book book) throws Exception {
        Document document = Files.exists(file) ? readDocument(file) : createEmptyDocument();
        appendBook(document, document.getDocumentElement(), book);
        save(document, file);
    }

    public static List<Book> findByAuthor(Path file, String author) throws Exception {
        return read(file).stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
    }

    public static List<Book> findByYear(Path file, int year) throws Exception {
        return read(file).stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }

    public static boolean removeByTitle(Path file, String title) throws Exception {
        if (!Files.exists(file)) {
            return false;
        }

        Document document = readDocument(file);
        NodeList nodes = document.getElementsByTagName("book");
        List<Element> matchingBooks = new ArrayList<Element>();

        for (int index = 0; index < nodes.getLength(); index++) {
            Element element = (Element) nodes.item(index);
            if (text(element, "title").equalsIgnoreCase(title)) {
                matchingBooks.add(element);
            }
        }

        for (Element book : matchingBooks) {
            book.getParentNode().removeChild(book);
        }

        if (!matchingBooks.isEmpty()) {
            save(document, file);
        }

        return !matchingBooks.isEmpty();
    }

    private static Document createEmptyDocument() throws Exception {
        Document document = newDocumentBuilderFactory().newDocumentBuilder().newDocument();
        Element root = document.createElement("library");
        document.appendChild(root);
        return document;
    }

    private static Document readDocument(Path file) throws Exception {
        Document document = newDocumentBuilderFactory().newDocumentBuilder().parse(file.toFile());
        document.getDocumentElement().normalize();
        return document;
    }

    private static DocumentBuilderFactory newDocumentBuilderFactory() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return factory;
    }

    private static void appendBook(Document document, Element root, Book book) {
        Element bookElement = document.createElement("book");
        bookElement.appendChild(element(document, "title", book.getTitle()));
        bookElement.appendChild(element(document, "author", book.getAuthor()));
        bookElement.appendChild(element(document, "year", Integer.toString(book.getYear())));
        root.appendChild(bookElement);
    }

    private static Element element(Document document, String name, String value) {
        Element element = document.createElement(name);
        element.appendChild(document.createTextNode(value));
        return element;
    }

    private static String text(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        return nodes.getLength() == 0 ? "" : nodes.item(0).getTextContent();
    }

    private static void save(Document document, Path file) throws Exception {
        Lab8Files.createParentDirectories(file);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(document), new StreamResult(file.toFile()));
    }
}
