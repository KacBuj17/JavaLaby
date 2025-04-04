import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class MultiplicationTable {
    private static final int defaultMinValue = 1;
    private static final int defaultMaxValue = 10;
    private static final int defaultMinRepetitions = 10;
    private static final int defaultMaxRepetitions = 25;
    private static final int defaultPercentage = 70;

    public static void main(String[] args) {
        Properties properties = new Properties();
        File propertiesFile = new File("settings.properties");

        int minValue = defaultMinValue;
        int maxValue = defaultMaxValue;
        int minRepetitions = defaultMinRepetitions;
        int maxRepetitions = defaultMaxRepetitions;
        int percentageThreshold = defaultPercentage;

        try {
            if (!propertiesFile.exists()) {
                properties.setProperty("wartość_minimum", String.valueOf(defaultMinValue));
                properties.setProperty("wartość_maximum", String.valueOf(defaultMaxValue));
                properties.setProperty("powtórzeń_minimum", String.valueOf(defaultMinRepetitions));
                properties.setProperty("powtórzeń_maximum", String.valueOf(defaultMaxRepetitions));
                properties.setProperty("procent", String.valueOf(defaultPercentage));
                properties.store(new FileOutputStream(propertiesFile), null);
                System.out.println("Default settings file created.");
            } else {
                properties.load(new FileInputStream(propertiesFile));
                minValue = Integer.parseInt(properties.getProperty("wartość_minimum"));
                maxValue = Integer.parseInt(properties.getProperty("wartość_maximum"));
                minRepetitions = Integer.parseInt(properties.getProperty("powtórzeń_minimum"));
                maxRepetitions = Integer.parseInt(properties.getProperty("powtórzeń_maximum"));
                percentageThreshold = Integer.parseInt(properties.getProperty("procent"));
            }
        } catch (IOException e) {
            System.out.println("Error loading properties file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int correctAnswers = 0;
        int totalQuestions = 0;

        long startTime = System.currentTimeMillis();

        while (totalQuestions < maxRepetitions) {
            int factor1 = random.nextInt(maxValue - minValue + 1) + minValue;
            int factor2 = random.nextInt(maxValue - minValue + 1) + minValue;
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
            if (totalQuestions >= minRepetitions) {
                double successRate = (correctAnswers / (double) totalQuestions) * 100;
                if (successRate >= percentageThreshold) {
                    break;
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long seconds = duration / 1000;

        double finalSuccessRate = (correctAnswers / (double) totalQuestions) * 100;
        System.out.println("Quiz finished!");
        System.out.println("Correct answers: " + correctAnswers + " out of " + totalQuestions);
        System.out.println("Your success rate: " + String.format("%.2f", finalSuccessRate) + "%");

        if (finalSuccessRate >= percentageThreshold) {
            System.out.println("Congratulations! You passed the quiz.");
        } else {
            System.out.println("You did not pass the quiz. Better luck next time!");
        }

        System.out.println("Time taken: " + seconds + " seconds.");
    }
}
