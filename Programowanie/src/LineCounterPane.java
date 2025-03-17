import javax.swing.JOptionPane;

public class LineCounterPane {
    public static void main(String[] args) {
        String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku:");

        if (fileName == null || fileName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nazwa pliku nie została podana.");
            return;
        }

        int lineCount = 0;
        try (java.io.FileReader fileReader = new java.io.FileReader(fileName);
             java.io.BufferedReader bufferedReader = new java.io.BufferedReader(fileReader)) {

            while (bufferedReader.readLine() != null) {
                lineCount++;
            }

            JOptionPane.showMessageDialog(null, "Liczba wierszy w pliku " + fileName + " wynosi: " + lineCount);
        } catch (java.io.FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Nie znaleziono pliku: " + fileName);
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas odczytu pliku: " + fileName);
        }
    }
}
