import javax.swing.*;
import java.awt.*;

public class ButtonsHandler {
    public static JPanel getButtonPanel(JTextField resultField) {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 15, 15));
        String[] buttons = {
                "1", "2", "3", "+",
                "4", "5", "6", "-",
                "7", "8", "9", "*",
                "0", "=", "C", "/"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 18));
            button.addActionListener(e -> CalculatorLogic.handleButtonClick(e.getActionCommand(), resultField));
            buttonPanel.add(button);
        }
        return buttonPanel;
    }
}
