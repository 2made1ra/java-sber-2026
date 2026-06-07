package lab8.algo.task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String first = reader.readLine();
        String second = reader.readLine();

        if (first == null) {
            first = "";
        }
        if (second == null) {
            second = "";
        }

        System.out.print(new Solution().distance(first, second));
    }
}
