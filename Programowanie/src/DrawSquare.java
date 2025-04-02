import java.util.Scanner;

public class DrawSquare {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the size of the square: ");
            int size = scanner.nextInt();

            if (size <= 0) {
                System.out.println("The size must be greater than 0.");
                return;
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                        System.out.print("#");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a valid integer.");
        }
    }
}
