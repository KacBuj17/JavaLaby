import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Brak argumentów programu.");
            System.out.println("Użycie: java FileCopy source_file target");
            return;
        }

        File sourceFile = new File(args[0]);
        File targetFile = new File(args[1]);

        if (!sourceFile.exists()) {
            System.out.println("Plik " + sourceFile.getName() + " nie istnieje.");
            return;
        }

        if (sourceFile.isDirectory()) {
            System.out.println(sourceFile.getName() + " jest katalogiem.");
            return;
        }

        if (!sourceFile.canRead()) {
            System.out.println("Brak dostępu do pliku " + sourceFile.getName());
            return;
        }

        if (targetFile.exists()) {
            if (targetFile.isDirectory()) {
                targetFile = new File(targetFile, sourceFile.getName());
            } else if (!targetFile.canWrite()) {
                System.out.println("Nie można nadpisać pliku " + targetFile.getName());
                return;
            }
        } else {
            File parentDir = targetFile.getParentFile();
            if (parentDir != null && (!parentDir.exists() || !parentDir.canWrite())) {
                System.out.println("Brak wymaganych uprawnień do katalogu " + parentDir);
                return;
            }
        }

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd podczas kopiowania pliku: " + e.getMessage());
        }
    }
}
