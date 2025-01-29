package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input string:");

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        scanner.close();

        System.out.println("The result:");
        System.out.println(encodeToChuckNorris(str));
    }

    public static String encodeToChuckNorris(String text) {
        StringBuilder binaryString = new StringBuilder();

        // Преобразуем каждый символ в 7-битный двоичный код
        for (char ch : text.toCharArray()) {
            String binaryChar = String.format("%7s", Integer.toBinaryString(ch)).replace(' ', '0');
            binaryString.append(binaryChar);
        }

        StringBuilder encodedString = new StringBuilder();
        char prevChar = binaryString.charAt(0);
        encodedString.append(prevChar == '1' ? "0 " : "00 ");
        encodedString.append("0");

        // Преобразование двоичного представления в унарный код
        for (int i = 1; i < binaryString.length(); i++) {
            char currentChar = binaryString.charAt(i);
            if (currentChar == prevChar) {
                encodedString.append("0");
            } else {
                encodedString.append(" ");
                encodedString.append(currentChar == '1' ? "0 " : "00 ");
                encodedString.append("0");
            }
            prevChar = currentChar;
        }

        return encodedString.toString();
    }
}
