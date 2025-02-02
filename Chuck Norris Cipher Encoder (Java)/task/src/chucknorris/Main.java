package chucknorris;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();

            switch (operation) {
                case "encode":
                    System.out.println("Input string:");
                    String input = scanner.nextLine();
                    System.out.println("Encoded string:");
                    System.out.println(encode(input));
                    break;

                case "decode":
                    System.out.println("Input encoded string:");
                    String encoded = scanner.nextLine();
                    if (isValidEncoded(encoded)) {
                        System.out.println("Decoded string:");
                        System.out.println(decode(encoded));
                    } else {
                        System.out.println("Encoded string is not valid.");
                    }
                    break;

                case "exit":
                    System.out.println("Bye!");
                    scanner.close();
                    return;

                default:
                    System.out.printf("There is no '%s' operation%n", operation);
            }
        }
    }

    public static String encode(String text) {
        StringBuilder binaryString = new StringBuilder();
        // Convert each character to 7-bit binary
        for (char ch : text.toCharArray()) {
            String binaryChar = String.format("%7s", Integer.toBinaryString(ch)).replace(' ', '0');
            binaryString.append(binaryChar);
        }

        StringBuilder result = new StringBuilder();
        char currentChar = binaryString.charAt(0);
        result.append(currentChar == '1' ? "0 " : "00 ");
        result.append("0");

        for (int i = 1; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == currentChar) {
                result.append("0");
            } else {
                currentChar = binaryString.charAt(i);
                result.append(" ").append(currentChar == '1' ? "0 " : "00 ").append("0");
            }
        }

        return result.toString();
    }

    public static String decode(String encoded) {
        // Convert Chuck Norris code to binary
        String[] blocks = encoded.split(" ");
        StringBuilder binary = new StringBuilder();

        for (int i = 0; i < blocks.length; i += 2) {
            char bit = blocks[i].equals("0") ? '1' : '0';
            binary.append(String.valueOf(bit).repeat(blocks[i + 1].length()));
        }

        // Convert binary to text
        StringBuilder result = new StringBuilder();
        String binaryStr = binary.toString();

        for (int i = 0; i < binaryStr.length(); i += 7) {
            String charBinary = binaryStr.substring(i, i + 7);
            result.append((char) Integer.parseInt(charBinary, 2));
        }

        return result.toString();
    }

    public static boolean isValidEncoded(String encoded) {
        // Check if string contains only 0's and spaces
        if (!encoded.matches("^[0 ]+$")) {
            return false;
        }

        // Split into blocks
        String[] blocks = encoded.split(" ");

        // Check if number of blocks is odd
        if (blocks.length % 2 != 0) {
            return false;
        }

        // Check each pair of blocks
        for (int i = 0; i < blocks.length; i += 2) {
            // Check if first block is not "0" or "00"
            if (!blocks[i].equals("0") && !blocks[i].equals("00")) {
                return false;
            }

            // Check if second block is empty or contains something other than zeros
            if (blocks[i + 1].isEmpty() || !blocks[i + 1].matches("^0+$")) {
                return false;
            }
        }

        // Convert to binary and check if length is multiple of 7
        String[] pairs = encoded.split(" ");
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < pairs.length; i += 2) {
            char bit = pairs[i].equals("0") ? '1' : '0';
            binary.append(String.valueOf(bit).repeat(pairs[i + 1].length()));
        }

        return binary.length() % 7 == 0;
    }
}