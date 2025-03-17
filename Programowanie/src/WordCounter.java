import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WordCounter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
            return;
        }

        boolean countLines = false;
        boolean countWords = false;
        boolean countChars = false;
        String fileName = null;

        for (String arg : args) {
            if (arg.equals("-l")) {
                countLines = true;
            } else if (arg.equals("-w")) {
                countWords = true;
            } else if (arg.equals("-c")) {
                countChars = true;
            } else if (arg.startsWith("-") && arg.contains("l")) {
                countLines = true;
            } else if (arg.startsWith("-") && arg.contains("w")) {
                countWords = true;
            } else if (arg.startsWith("-") && arg.contains("c")) {
                countChars = true;
            } else if (!arg.startsWith("-")) {
                fileName = arg;
            }
        }

        if (!countLines && !countWords && !countChars) {
            countLines = true;
            countWords = true;
            countChars = true;
        }

        if (fileName == null) {
            for (String arg : args) {
                if (!arg.startsWith("-")) {
                    fileName = arg;
                    break;
                }
            }
        }

        if (fileName == null) {
            System.out.println("Nie podano nazwy pliku.");
            return;
        }

        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                wordCount += line.trim().isEmpty() ? 0 : line.split("\\s+").length;
            }

            if (countLines) {
                System.out.println("wierszy: " + lineCount);
            }
            if (countChars) {
                System.out.println("znaków: " + charCount);
            }
            if (countWords) {
                System.out.println("słów: " + wordCount);
            }

        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas odczytu pliku: " + fileName);
        }
    }
}