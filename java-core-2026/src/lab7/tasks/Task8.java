package lab7.tasks;

import lab7.common.InputUtils;
import lab7.common.Lab7Files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Scanner;

public class Task8 {
    public static void saveStudent(Path file, Student student) throws IOException {
        Lab7Files.createParentDirectories(file);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            outputStream.writeObject(student);
        }
    }

    public static Student loadStudent(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            return (Student) inputStream.readObject();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String name = InputUtils.readLineOrDefault(scanner, "Enter student name", "Andrey");
        int age = InputUtils.readIntOrDefault(scanner, "Enter student age", 21);
        String group = InputUtils.readLineOrDefault(scanner, "Enter group", "RIM-150950");
        double averageGrade = InputUtils.readDoubleOrDefault(scanner, "Enter average grade", 4.75);
        Path file = Lab7Files.demoDirectory("task8").resolve("student.bin");

        Student student = new Student(name, age, group, averageGrade);
        saveStudent(file, student);
        System.out.println("Object saved to file: " + file.toAbsolutePath());
        System.out.println("Object loaded from file: " + loadStudent(file));
    }

    public static class Student implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int age;
        private final String group;
        private final double averageGrade;

        public Student(String name, int age, String group, double averageGrade) {
            this.name = name;
            this.age = age;
            this.group = group;
            this.averageGrade = averageGrade;
        }

        @Override
        public String toString() {
            return "Student{name='" + name + "', age=" + age
                    + ", group='" + group + "', averageGrade=" + averageGrade + "}";
        }
    }
}
