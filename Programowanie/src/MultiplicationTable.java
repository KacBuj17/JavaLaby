import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class MultiplicationTable {
    private static final int defaultMinimumValue = 1;
    private static final int defaultMaximumValue = 10;
    private static final int defaultMinimumRepetitions = 10;
    private static final int defaultMaximumRepetitions = 25;
    private static final int defaultPercentage = 70;

    public static void main(String[] args) {
        Properties properties = new Properties();
        File propertiesFile = new File("settings.properties");

        int minimumValue = defaultMinimumValue;
        int maximumValue = defaultMaximumValue;
        int minimumRepetitions = defaultMinimumRepetitions;
        int maximumRepetitions = defaultMaximumRepetitions;
        int percentageThreshold = defaultPercentage;

        try {
            if (!propertiesFile.exists()) {
                properties.setProperty("wartość_minimum", String.valueOf(defaultMinimumValue));
                properties.setProperty("wartość_maximum", String.valueOf(defaultMaximumValue));
                properties.setProperty("powtórzeń_minimum", String.valueOf(defaultMinimumRepetitions));
                properties.setProperty("powtórzeń_maximum", String.valueOf(defaultMaximumRepetitions));
                properties.setProperty("procent", String.valueOf(defaultPercentage));
                properties.store(new FileOutputStream(propertiesFile), null);
                System.out.println("Default settings file created.");
            } else {
                properties.load(new FileInputStream(propertiesFile));
                minimumValue = Integer.parseInt(properties.getProperty("wartość_minimum"));
                maximumValue = Integer.parseInt(properties.getProperty("wartość_maximum"));
                minimumRepetitions = Integer.parseInt(properties.getProperty("powtórzeń_minimum"));
                maximumRepetitions = Integer.parseInt(properties.getProperty("powtórzeń_maximum"));
                percentageThreshold = Integer.parseInt(properties.getProperty("procent"));
            }
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int correctAnswers = 0;
        int totalQuestions = 0;

        while (totalQuestions < maximumRepetitions) {
            int factor1 = random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
            int factor2 = random.nextInt(maximumValue - minimumValue + 1) + minimumValue;
            System.out.print(factor1 + " * " + factor2 + " = ");

            try {
                int userAnswer = scanner.nextInt();
                if (userAnswer == factor1 * factor2) {
                    System.out.println("Correct!");
                    correctAnswers++;
                } else {
                    System.out.println("Wrong answer. The correct answer is: " + (factor1 * factor2));
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            totalQuestions++;
            if (totalQuestions >= minimumRepetitions) {
                double successRate = (correctAnswers / (double) totalQuestions) * 100;
                if (successRate >= percentageThreshold) {
                    break;
                }
            }
        }

        double finalSuccessRate = (correctAnswers / (double) totalQuestions) * 100;
        System.out.println("Quiz finished!");
        System.out.println("Correct answers: " + correctAnswers + " out of " + totalQuestions);
        System.out.println("Your success rate: " + String.format("%.2f", finalSuccessRate) + "%");

        if (finalSuccessRate >= percentageThreshold) {
            System.out.println("Congratulations! You passed the quiz.");
        } else {
            System.out.println("You did not pass the quiz. Better luck next time!");
        }
    }
}
