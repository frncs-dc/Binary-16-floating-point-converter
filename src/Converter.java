import javax.swing.*;
import java.util.Arrays;

public class Converter extends JFrame{
    private JButton convertBtn;
    private JLabel title;
    private JPanel converterPanel;
    private JTextField inputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton outputInTextFileButton;

    /*
        @param  input   - the input to check if binary or decimal
        @return true if binary value, false if decimal
    */
    static boolean checkBinary(String input) {
        return input.contains("x2");
    }

    public Converter() {
        this.setContentPane(this.converterPanel);
        this.setTitle("Binary-16 floating point converter");
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
