package lab7.common;

import java.util.Scanner;

public final class InputUtils {
    private InputUtils() {
    }

    public static String readLineOrDefault(Scanner scanner, String prompt, String defaultValue) {
        System.out.print(prompt + " [" + defaultValue + "]: ");
        if (scanner.hasNextLine()) {
            String value = scanner.nextLine();
            if (!value.trim().isEmpty()) {
                return value;
            }
        }

        System.out.println("Using default value: " + defaultValue);
        return defaultValue;
    }

    public static int readIntOrDefault(Scanner scanner, String prompt, int defaultValue) {
        String value = readLineOrDefault(scanner, prompt, Integer.toString(defaultValue));
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect number, using default value: " + defaultValue);
            return defaultValue;
        }
    }

    public static double readDoubleOrDefault(Scanner scanner, String prompt, double defaultValue) {
        String value = readLineOrDefault(scanner, prompt, Double.toString(defaultValue));
        try {
            return Double.parseDouble(value.trim().replace(',', '.'));
        } catch (NumberFormatException exception) {
            System.out.println("Incorrect number, using default value: " + defaultValue);
            return defaultValue;
        }
    }
}
