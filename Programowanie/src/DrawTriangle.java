import java.util.Scanner;

public class DrawTriangle {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the height of the triangle: ");
            int height = scanner.nextInt();

            if (height <= 0) {
                System.out.println("The height must be greater than 0.");
                return;
            }

            for (int i = 1; i <= height; i++) {
                for (int j = 0; j < height - i; j++) {
                    System.out.print(" ");
                }
                for (int k = 0; k < 2 * i - 1; k++) {
                    System.out.print("#");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid integer.");
        }
    }
}