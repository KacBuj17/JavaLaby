import javax.swing.*;
import java.awt.*;

public class CalculatorLogic {
    private static int operand1 = 0;
    private static int operand2 = 0;
    private static String operator = "";
    private static boolean isResultDisplayed = false;
    private static boolean lastActionEquals = false;

    public static void handleButtonClick(String buttonText, JTextField resultField) {
        try {
            if ("0123456789".contains(buttonText)) {
                handleDigit(buttonText, resultField);
            } else if ("+-*/".contains(buttonText)) {
                handleOperator(buttonText, resultField);
            } else if ("=".equals(buttonText)) {
                handleEquals(resultField);
            } else if ("C".equals(buttonText)) {
                resetCalculator(resultField);
            }
        } catch (ArithmeticException ex) {
            resultField.setForeground(Color.RED);
            resultField.setText("ERROR: Division by zero");
            resetCalculator(resultField);
        } catch (Exception ex) {
            resultField.setForeground(Color.RED);
            resultField.setText("Invalid input");
            resetCalculator(resultField);
        }
    }

    private static void handleDigit(String buttonText, JTextField resultField) {
        if (lastActionEquals) {
            resetCalculator(resultField);
        }
        if (isResultDisplayed && !operator.isEmpty()) {
            resultField.setForeground(Color.BLACK);
            resultField.setText(buttonText);
            isResultDisplayed = false;
        } else if (resultField.getText().equals("0")) {
            resultField.setForeground(Color.BLACK);
            resultField.setText(buttonText);
        } else {
            resultField.setText(resultField.getText() + buttonText);
        }
    }

    private static void handleOperator(String buttonText, JTextField resultField) {
        lastActionEquals = false;
        if (!operator.isEmpty() && !isResultDisplayed) {
            operand2 = Integer.parseInt(resultField.getText());
            operand1 = calculate(operand1, operand2, operator);
            resultField.setText(String.valueOf(operand1));
        } else {
            operand1 = Integer.parseInt(resultField.getText());
        }
        operator = buttonText;
        isResultDisplayed = true;
    }

    private static void handleEquals(JTextField resultField) {
        if (operator.isEmpty()) {
            lastActionEquals = true;
        } else {
            if (lastActionEquals) {
                operand1 = calculate(operand1, operand2, operator);
            } else {
                if (isResultDisplayed) {
                    operand2 = operand1;
                } else {
                    operand2 = Integer.parseInt(resultField.getText());
                }
                operand1 = calculate(operand1, operand2, operator);
            }
            resultField.setForeground(Color.BLACK);
            resultField.setText(String.valueOf(operand1));
            isResultDisplayed = true;
            lastActionEquals = true;
        }
    }

    private static int calculate(int num1, int num2, String op) throws ArithmeticException {
        return switch (op) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> {
                if (num2 == 0) throw new ArithmeticException("Division by zero");
                yield num1 / num2;
            }
            default -> num1;
        };
    }

    private static void resetCalculator(JTextField resultField) {
        operand1 = 0;
        operand2 = 0;
        operator = "";
        isResultDisplayed = false;
        lastActionEquals = false;
        resultField.setText("0");
        resultField.setForeground(Color.BLACK);
    }
}
