import javax.swing.*;
import java.awt.*;

public class CalculatorLogic {
    private static int number1 = 0;
    private static int number2 = 0;
    private static String currentOperator = "";
    private static boolean isResultShown = false;
    private static boolean lastActionWasEquals = false;

    public static void handleButtonClick(String buttonText, JTextField resultField) {
        try {
            if ("0123456789".contains(buttonText)) {
                handleDigit(buttonText, resultField);
            } else if ("+-*/".contains(buttonText)) {
                handleOperator(buttonText, resultField);
            } else if ("=".equals(buttonText)) {
                handleEquals(resultField);
            } else if ("C".equals(buttonText)) {
                handleResetCalculator(resultField);
            }
        } catch (ArithmeticException ex) {
            resultField.setForeground(Color.RED);
            resultField.setText("ERROR: Division by zero");
            handleResetCalculator(resultField);
        } catch (Exception ex) {
            resultField.setForeground(Color.RED);
            resultField.setText("Invalid input");
            handleResetCalculator(resultField);
        }
    }

    private static void handleDigit(String buttonText, JTextField resultField) {
        if (lastActionWasEquals) {
            handleResetCalculator(resultField);
        }
        if (isResultShown && !currentOperator.isEmpty()) {
            resultField.setForeground(Color.BLACK);
            resultField.setText(buttonText);
            isResultShown = false;
        } else if (resultField.getText().equals("0")) {
            resultField.setForeground(Color.BLACK);
            resultField.setText(buttonText);
        } else {
            resultField.setText(resultField.getText() + buttonText);
        }
    }

    private static void handleOperator(String buttonText, JTextField resultField) {
        lastActionWasEquals = false;
        if (!currentOperator.isEmpty() && !isResultShown) {
            number2 = Integer.parseInt(resultField.getText());
            number1 = handleCalculate(number1, number2, currentOperator);
            resultField.setText(String.valueOf(number1));
        } else {
            number1 = Integer.parseInt(resultField.getText());
        }
        currentOperator = buttonText;
        isResultShown = true;
    }

    private static void handleEquals(JTextField resultField) {
        if (!currentOperator.isEmpty()) {
            if (!lastActionWasEquals) {
                if (isResultShown) {
                    number2 = number1;
                } else {
                    number2 = Integer.parseInt(resultField.getText());
                }
            }
            number1 = handleCalculate(number1, number2, currentOperator);
            resultField.setForeground(Color.BLACK);
            resultField.setText(String.valueOf(number1));
            isResultShown = true;
        }
        lastActionWasEquals = true;
    }

    private static int handleCalculate(int number1, int number2, String operation) {
        try {
            return switch (operation) {
                case "+" -> number1 + number2;
                case "-" -> number1 - number2;
                case "*" -> number1 * number2;
                case "/" -> number1 / number2;
                default -> number1;
            };
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
            return 0;
        }
    }


    private static void handleResetCalculator(JTextField resultField) {
        number1 = 0;
        number2 = 0;
        currentOperator = "";
        isResultShown = false;
        lastActionWasEquals = false;
        resultField.setText("0");
        resultField.setForeground(Color.BLACK);
    }
}
