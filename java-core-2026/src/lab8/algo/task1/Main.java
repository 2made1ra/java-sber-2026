package lab8.algo.task1;

import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FastScanner scanner = new FastScanner();
        int size = scanner.nextInt();
        int[] sequence = new int[size];

        for (int i = 0; i < size; i++) {
            sequence[i] = scanner.nextInt();
        }

        System.out.print(new Solution().format(sequence));
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

            int sign = 1;
            if (current == '-') {
                sign = -1;
                current = read();
            }

            int value = 0;
            while (current > ' ') {
                value = value * 10 + current - '0';
                current = read();
            }

            return value * sign;
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
