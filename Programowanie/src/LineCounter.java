public class LineCounter {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
            return;
        }

        String fileName = args[0];
        int lineCount = 0;

        try (java.io.FileReader fileReader = new java.io.FileReader(fileName);
             java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader)) {

            while (bufferedReader.readLine() != null) {
                lineCount++;
            }

            System.out.println("Liczba wierszy w pliku " + fileName + " wynosi: " + lineCount);
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku: " + fileName);
        } catch (java.io.IOException e) {
            System.out.println("Wystąpił błąd podczas odczytu pliku: " + fileName);
        }
    }
}
