package lab7.algo.task2;

import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        System.out.print(new Solution().countRoutes(rows, columns));
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int current = read();
            while (current <= ' ' && current != -1) {
                current = read();
            }

            int value = 0;
            while (current > ' ') {
                value = value * 10 + current - '0';
                current = read();
            }

            return value;
        }

        private int read() throws IOException {
            if (pointer >= length) {
                length = input.read(buffer);
                pointer = 0;
                if (length <= 0) {
                    return -1;
                }
            }

            int value = buffer[pointer];
            pointer++;
            return value;
        }
    }
}
