package lab7.examples;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Example7 {
    public static void serialize(Path file, Person person) throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            outputStream.writeObject(person);
        }
    }

    public static Person deserialize(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (Person) inputStream.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String name = InputUtils.readLineOrDefault(scanner, "Enter person name", "Ivan");
        int age = InputUtils.readIntOrDefault(scanner, "Enter person age", 25);
        String city = InputUtils.readLineOrDefault(scanner, "Enter person city", "Ekaterinburg");

        Person person = new Person(name, age, city);
        Path file = Lab7Files.demoDirectory("example7").resolve("person.bin");

        serialize(file, person);
        System.out.println("Object serialized to file: " + file.toAbsolutePath());
        System.out.println("Object restored from file: " + deserialize(file));

        Files.deleteIfExists(file);
    }

    public static class Person implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int age;
        private final String city;

        public Person(String name, int age, String city) {
            this.name = name;
            this.age = age;
            this.city = city;
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", city='" + city + "'}";
        }
    }
}
