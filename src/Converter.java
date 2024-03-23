import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Converter extends JFrame {
    private JButton convertBtn;
    private JLabel title;
    private JPanel converterPanel;
    private JTextField inputField;
    private JLabel inputLabel;
    private JLabel outputLabel;
    private JButton outputInTextFileButton;
    private JLabel signBitField;
    private JLabel expField;
    private JLabel mantissaField;
    private JLabel hexField;
    private JLabel errorMsg;

    /**
     * @param input the input to check if binary or decimal
     * @return true if binary value, false if decimal
     */
    static boolean checkBinary(String input) {
        return input.contains("x2");
    }

    /**
     * @param input to convert to Binary
     * @return  converted Binary
     */
    public static String decimalToBinary(String input){ //  function to convert decimal to binary

        BigDecimal ZERO = new BigDecimal("0");
        BigDecimal TWO = new BigDecimal("2");
        BigDecimal ONE = new BigDecimal("1");
        BigDecimal wholeNum;
        BigDecimal deciNum;
        String finalBinary = "";

        if (input.contains(".")) {
            String[] decimalNum = input.split("\\.");
            wholeNum = new BigDecimal(decimalNum[0]);
            deciNum =  new BigDecimal("0." + decimalNum[1]);
            System.out.println("CONTAINS DECIMAL");
        } else {
            String[] decimalNum = input.split("\\.");
            wholeNum = new BigDecimal(decimalNum[0]);
            deciNum = new BigDecimal("0.0");
            System.out.println("CONTAINS NO DECIMAL");
        }

//        if (input.contains(".")) {  // if given string has a decimal value
//            String[] floatingNumber = input.split("\\.");
//            wholeNumber = Integer.parseInt(floatingNumber[0]); // whole number value
//            String strDeciNumber = "0." + Integer.parseInt(floatingNumber[1]);
//            deciNumber = Float.parseFloat(strDeciNumber); // decimal point value
//        } else { // if  given does not have a decimal value
//            String[] floatingNumber = input.split("\\.");
//            wholeNumber = Integer.parseInt(floatingNumber[0]); // whole number value
//            deciNumber = 0.0f;
//        }
//        gets the exponent
//        String[] exponent = inputs[1].split("\\^");
//        int exp = Integer.parseInt(exponent[1]);

        int[] binaryNum = new int[1000];
        int i = 0;
//        System.out.println("THIS NUM: " + wholeNum);
        while (wholeNum.compareTo(ZERO) > 0) {
            binaryNum[i] = Integer.parseInt(String.valueOf(wholeNum.remainder(TWO)));
//            System.out.println("CURRENT INPUT: " + binaryNum[i]);
//            System.out.println("OLD NUM: " + wholeNum);
            wholeNum = wholeNum.divide(TWO).setScale(0, RoundingMode.FLOOR);
//            System.out.println("NEW NUM: " + wholeNum);
            i++;
        }

//        while(wholeNumber > 0){
//            binaryNum[i] = wholeNumber % 2;  //remainder is stored
//            wholeNumber = wholeNumber / 2;   //divide for next iteration until n = 0
//            i++;
//        }

        for(int j = i - 1; j >= 0; j--){// printing of binary in reverse order
            finalBinary = finalBinary + binaryNum[j];  //concat reversely the array that contains the modulo by 2
            // as we store the modulo from end to  start
        }
//        System.out.println("CURRENT WHOLE NUM: " + finalBinary);

        int MAX = 0;
        if (deciNum.compareTo(ZERO) > 0) {
            finalBinary += ".";
//            System.out.println("WHOLE NUM WITH DEC: " + finalBinary);
            while (deciNum.compareTo(ZERO) > 0 && MAX != 20) {
//                System.out.println("DEC: " + deciNum);
                deciNum = deciNum.multiply(TWO);
//                System.out.println("DEC MUL: " + deciNum);
                if (deciNum.compareTo(ONE) > 0 || deciNum.compareTo(ONE) == 0) {
                    deciNum = deciNum.subtract(ONE);
                    finalBinary += "1";
                } else {
                    finalBinary += "0";
                }
                MAX++;
            }
        }

//        if(deciNumber > 0.0000){
//            finalBinary += "."; // appending the decimal point
//            while(deciNumber > 0.0){
//                deciNumber *= 2;
//                if(deciNumber >= 1.0){
//                    deciNumber -= 1;  //if the product of multiplying by 2 is more than 1.(some number)  subtract 1
//                    finalBinary += "1"; //  if true we now append 1
//                } else {
//                    finalBinary += "0"; // if not more than 1 append 0
//                }
//            }
//        }

        System.out.println("Binary:" + finalBinary);
        return finalBinary; // returns the converted decimal to binary as string
    }

    static String getHexLetter(int value){
        switch (value){
            case 10:
                return "A";
            case 11:
                return "B";
            case 12:
                return "C";
            case 13:
                return "D";
            case 14:
                return "E";
            case 15:
                return "F";
            default:
                return String.valueOf(value);
        }
    }

    static String convertToHex(String binaryInput){
        String[] binGroups = new String[4];
        int[] hexValues = new int[]{8, 4, 2, 1};
        int[] hexRawValue = new int[4];
        String finalHex = "";
        int beginIndex = 0;
        int endIndex = 4;

        for(int i = 0; i < 4; i++){
            binGroups[i] = binaryInput.substring(beginIndex,endIndex);
            beginIndex+=4;
            endIndex+=4;
        }

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++) {
                hexRawValue[i] += (binGroups[i].charAt(j) - '0') * hexValues[j];
            }
            finalHex += getHexLetter(hexRawValue[i]);
        }

        return finalHex;
    }

    static String checkNegative (String input, Output output) {
        if (input.startsWith("-")){
            output.sign = "1";
            return input.substring(1);
        } else {
            output.sign = "0";
            return input;
        }
    }
    private void writeOutputToTextFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Output as Text File");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Append ".txt" extension if not present
                if (!filePath.toLowerCase().endsWith(".txt")) {
                    filePath += ".txt";
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                writer.write(content);
                writer.close();
                JOptionPane.showMessageDialog(this, "Output has been written to the file: " + filePath, "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkValidBinary(String input) throws Exception {
        String[] binaryString = input.split("x2");
        for (int i = 0; i < binaryString[0].length(); i++) {
            // If the character is not '0' or '1', return false
            if (    binaryString[0].charAt(i) != '0' &&
                    binaryString[0].charAt(i) != '1' &&
                    binaryString[0].charAt(i) != '.' &&
                    binaryString[0].charAt(i) != '-'
            ) {
                throw new Exception();
            }
        }
    }

    private boolean checkNan(String input, Output output) {
        if(input.equalsIgnoreCase("snan")){
            output.sign = "X";
            output.exponentBias = "11111";
            output.mantissa = "01XXXXXXXX";

            signBitField.setText(output.sign);
            expField.setText(output.exponentBias);
            mantissaField.setText(output.mantissa);
            String lowerHexRange = convertToHex("1"+output.exponentBias+"0111111111");
            String upperHexRange = convertToHex("0"+output.exponentBias+"0111111111");
            hexField.setText(lowerHexRange + " - " + upperHexRange);
            outputInTextFileButton.setEnabled(true);

            return true;

        } else if (input.equalsIgnoreCase("qnan")){
            output.sign = "X";
            output.exponentBias = "11111";
            output.mantissa = "1XXXXXXXXX";

            signBitField.setText(output.sign);
            expField.setText(output.exponentBias);
            mantissaField.setText(output.mantissa);
            String lowerHexRange = convertToHex("1"+output.exponentBias+"1111111111");
            String upperHexRange = convertToHex("0"+output.exponentBias+"1111111111");
            hexField.setText(lowerHexRange + " - " + upperHexRange);
            outputInTextFileButton.setEnabled(true);

            return true;
        }

        return false;
    }

    public Converter() {

        convertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Output output = new Output();
                String binaryNum;
                String convertedNum;
                String input = inputField.getText();

                try {
                    errorMsg.setText("");
                    if (!checkNan(input, output)){
                        input = checkNegative(input, output);

                        if (!checkBinary(input)){ // check if Binary
                            binaryNum = decimalToBinary(output.expandDecimal(input)); // if not convert
                            convertedNum = output.convertTo1f(binaryNum);
                            // check if special case
                            if(!output.isSpecialCase(convertedNum)){
                                output.computeBias();
                                output.completeMantissa(convertedNum);
                            }
                        } else if (checkBinary(input)) {
                            checkValidBinary(input);
                            convertedNum = output.convertTo1f(output.expandBinary(input));
                            // check if special case
                            if(!output.isSpecialCase(convertedNum)){
                                output.computeBias();
                                output.completeMantissa(convertedNum);
                            }
                        }

                        signBitField.setText(output.sign);
                        expField.setText(output.exponentBias);
                        mantissaField.setText(output.mantissa);
                        hexField.setText(convertToHex(output.sign+output.exponentBias+output.mantissa));
                        outputInTextFileButton.setEnabled(true);
                    }

                } catch (Exception error) {
                    System.out.println(error);
                    errorMsg.setText("ERROR: Invalid Input. Please try again.");
                    signBitField.setText("");
                    expField.setText("");
                    mantissaField.setText("");
                    hexField.setText("");
                    outputInTextFileButton.setEnabled(false);
                }


            }
        });

        outputInTextFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = "Sign Bit: " + signBitField.getText() + "\n" +
                        "Exponent: " + expField.getText() + "\n" +
                        "Mantissa: " + mantissaField.getText() + "\n" +
                        "Hexadecimal: " + hexField.getText();
                writeOutputToTextFile(content);
            }
        });

        this.setContentPane(this.converterPanel);
        this.setTitle("Binary-16 floating point converter");
        this.setSize(700,400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
