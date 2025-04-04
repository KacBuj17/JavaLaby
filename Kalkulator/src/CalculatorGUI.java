import javax.swing.*;
import java.awt.*;

public class CalculatorGUI {
    public static void createAndShowGUI() {
        JFrame jf = new JFrame("Kalkulator");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(350, 250);
        jf.setLocationRelativeTo(null);
        jf.setResizable(true);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextField resultField = new JTextField("0");
        resultField.setHorizontalAlignment(SwingConstants.RIGHT);
        resultField.setFont(new Font("Consolas", Font.BOLD, 24));
        resultField.setEditable(false);
        mainPanel.add(resultField, BorderLayout.NORTH);

        JPanel buttonPanel = ButtonsHandler.getButtonPanel(resultField);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        jf.add(mainPanel);
        jf.setVisible(true);
    }
}
