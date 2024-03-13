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


    /**
     * @param input 
     * @return
     */
    public static String decimal_toBinary(String input){
//      String[] inputs = input.split("x10");
        int wholeNumber = 0;
        float deciNumber = 0.0f;

        if(input.contains(".")) {
            String[] inputs = input.split("\\.");
            wholeNumber = Integer.parseInt(inputs[0]); // whole number value
            deciNumber = 0.0f;
            String strDeciNumber = "0." + Integer.parseInt(inputs[1]);
            deciNumber = Float.parseFloat(strDeciNumber); // decimal point value
        }
        else{
            String[] inputs = input.split("\\.");
            wholeNumber = Integer.parseInt(inputs[0]); // whole number value
            deciNumber = 0.0f;
        }
//        gets the exponent
//        String[] exponent = inputs[1].split("\\^");
//        int exp = Integer.parseInt(exponent[1]);

        int[] binaryNum = new int[1000];
        int i = 0;
        while(wholeNumber > 0){
            binaryNum[i] = wholeNumber % 2;  //remainder is stored
            wholeNumber = wholeNumber / 2;             //divide for next iteration until n = 0
            i++;
        }

        String finalBinary = "";

        for(int j = i - 1; j >= 0; j--){// printing of binary in reverse order
            finalBinary = finalBinary + binaryNum[j];
        }

        if(deciNumber > 0.0000){
            finalBinary += "."; // appending the decimal point

            while(deciNumber > 0.0){
                deciNumber *= 2;

                if(deciNumber >= 1.0){
                    deciNumber -= 1;
                    finalBinary += "1";
                }
                else {
                    finalBinary += "0";
                }
            }
        }

        return String.valueOf(finalBinary);
    }

    /** @param input 
     *
     */
    static String expand(String input){
        String[] expandedNumber = input.split("[x^]");
        double number = Double.parseDouble(expandedNumber[0]);
        int base = Integer.parseInt(expandedNumber[1]);
        int exponent = Integer.parseInt(expandedNumber[2]);

        return String.valueOf(number * Math.pow(base, exponent));
    }

    public Converter() {

        String input = inputField.getText();
        boolean negative = input.contains("-");
        if (negative){input = input.substring(1);}

        if (!checkBinary(input)){
            decimal_toBinary(expand(input));
        }



        this.setContentPane(this.converterPanel);
        this.setTitle("Binary-16 floating point converter");
        this.setSize(300,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
