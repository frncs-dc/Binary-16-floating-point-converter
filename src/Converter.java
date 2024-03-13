import javax.swing.*;

public class Converter extends JFrame{
    private JButton convertBtn;
    private JLabel title;
    private JPanel converterPanel;
    private JTextField inputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton outputInTextFileButton;

    public Converter() {
        this.setContentPane(this.converterPanel);
        this.setTitle("Binary-16 floating point converter");
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
