import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class FileAndURLCopy {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Brak argumentów programu.");
            System.out.println("Użycie:");
            System.out.println("  java FileAndURLCopy source target");
            System.out.println("  java FileAndURLCopy http://example.com/file.html [optional_target]");
            return;
        }

        String source = args[0];
        String target = args.length > 1 ? args[1] : null;

        if (isValidURL(source)){
            copyFromURL(source, target);
        }
    }

    private static boolean isValidURL(String url) {
        String urlPattern = "^(http|https)://.*$";
        return Pattern.matches(urlPattern, url);
    }

    private static void copyFromURL(String sourceURL, String target) {
        HttpURLConnection connection = null;

        try{
            URL url = new URL(sourceURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Brak dostępu do '" + sourceURL + "'. Kod odpowiedzi: " + responseCode);
                return;
            }

            String fileName = target;
            if (fileName == null) {
                String[] urlParts = sourceURL.split("/");
                fileName = urlParts[urlParts.length - 1];
                if (fileName.isEmpty()) {
                    fileName = "default.html";
                }
            }

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(fileName)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                System.out.println("Plik pobrany i zapisany jako: " + fileName);
            }
        } catch (MalformedURLException e) {
            System.out.println("Podany adres: '" + sourceURL + "' jest nieprawidłowy.");
        } catch (IOException e) {
            if (e.getMessage().contains("timed out")) {
                System.out.println("Przekroczono czas oczekiwania podczas próby połączenia z URL: " + sourceURL);
            } else {
                System.out.println("Błąd podczas pobierania pliku z URL: " + e.getMessage());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}