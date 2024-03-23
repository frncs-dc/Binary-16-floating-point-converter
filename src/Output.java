import java.math.BigDecimal;

public class Output {

    //attributes
    public String sign;
    public String exponent;
    public String mantissa;
    public String exponentBias;

    //util
    private final int bias = 15;

    /**
     *
     * @param binaryInput string to check if its a special case
     * @return true if special case, false if not
     */
    public boolean isSpecialCase (String binaryInput){
        String[] exp = binaryInput.split("x2\\^");
        for (String a : exp){
            System.out.println(a);
        }

        System.out.println("BIN: " + binaryInput);
        System.out.println("DBIN: " + Double.parseDouble(binaryInput));

        //super large number
        if(Integer.parseInt(exponent) > 15){
            this.exponentBias = "11111";
            this.mantissa = "0000000000";
            return true;
        }
        //zero
        else if (Double.parseDouble(binaryInput) == 0) {
            this.exponentBias = "00000";
            this.mantissa = "0000000000";
            return true;
        }
        //super small number, < x2^-14
        else if (Integer.parseInt(exponent) < -14)
        {
            System.out.println("less than -14");

            this.exponentBias = "00000";

            this.mantissa = calculateDenormalizedMantissa(binaryInput, (Integer.parseInt(exponent) + 14) * -1);
            return true;
        }

        return false;
    }

    /**
     *
     * @param binary string to denormalize
     * @param shiftDecimal the exponent
     * @return
     */
    public String calculateDenormalizedMantissa(String binary, int shiftDecimal){
        System.out.println("My binary is" + binary + " and my exponent to shift is " + shiftDecimal);
        //shift decimal place to left for each shiftDecimal values
        BigDecimal binaryBigDecimal = new BigDecimal(binary);
        BigDecimal pointOne = new BigDecimal("0.1");
        while(shiftDecimal > 0){
            binaryBigDecimal = binaryBigDecimal.multiply(pointOne);
            shiftDecimal--;
        }
        // ensure the mantissa is 10 bits
        System.out.println("My double is " + binaryBigDecimal.toPlainString());
        String binaryString = binaryBigDecimal.toPlainString();
        binaryString = binaryString.substring(2);

        while(binaryString.length() < 10){
            binaryString += "0";
        }
        if(binaryString.length() > 10){
            binaryString = binaryString.substring(0, 10);
        }

        System.out.println("My binary string is " + binaryString);
        return binaryString;
    }


    /**
     *
     * @param input the string to expand
     * @return the expanded string
     */
    public String expandDecimal(String input){
        String[] expandedNumber = input.split("[x^]");
        BigDecimal ZERO = new BigDecimal("0");

        this.exponent = "0";

        BigDecimal baseBD = new BigDecimal(expandedNumber[1]);
        BigDecimal expBD = new BigDecimal(expandedNumber[2]);
        BigDecimal power = BigDecimal.ONE;
        int exp = Integer.parseInt(expandedNumber[2]);

        if (expBD.compareTo(ZERO) > 0){
            for (int i = 0; i < exp; i++){
                power = power.multiply(baseBD);
            }
        } else if (expBD.compareTo(ZERO) < 0) {
            for (int i = 0; i > exp; i--){
                power = power.divide(baseBD);
            }
        }

        System.out.println("POWERER: " + power);

        BigDecimal number = new BigDecimal(expandedNumber[0]);

        System.out.println("EXPAND: " + power.multiply(number));

        return power.multiply(number).toPlainString();
    }

    /**
     *
     * @param input the string to expand
     * @return the expanded string
     */
    public String expandBinary(String input){
        String[] expandedNumber = input.split("[x^]");
        double number = Double.parseDouble(expandedNumber[0]);
        int base = Integer.parseInt(expandedNumber[1]);
        int exp = Integer.parseInt(expandedNumber[2]);
        this.exponent = String.valueOf(exp);

        return expandedNumber[0];
    }

    public void computeBias(){
        String exponentDecimal = String.valueOf(Integer.parseInt(exponent) + bias);
        String tempBias = Converter.decimalToBinary(exponentDecimal);
        while (tempBias.length() < 5) {
            tempBias = "0" + tempBias;
        }
        this.exponentBias = tempBias;

    }

    /**
     *
     * @param unconvertedNum the string to convert
     * @return the converted string
     */
    public String convertTo1f(String unconvertedNum){
        BigDecimal binaryNum = new BigDecimal(unconvertedNum);
        BigDecimal mul1 = new BigDecimal("10");
        BigDecimal mul2 = new BigDecimal("0.1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal one = new BigDecimal("1");
        BigDecimal zero = new BigDecimal("0");
        int minusExponent = 0;
        int addExponent = 0;
        if( (binaryNum.compareTo(two) < 0 && binaryNum.compareTo(one) > 0) || binaryNum.compareTo(one) == 0 || binaryNum.compareTo(zero) == 0){
            return String.valueOf(binaryNum);
        }
        // binaryNum 0.XX
        else if (binaryNum.compareTo(one) < 0){
            while(binaryNum.compareTo(one) < 0){
                binaryNum = binaryNum.multiply(mul1);
//                System.out.println("disis" + binaryNum);
                minusExponent++;
//                System.out.println("MINUS E: " + minusExponent);
            }
        }
        //binaryNum 1XX
        else if (binaryNum.compareTo(two) > 0){
            while(binaryNum.compareTo(two) > 0){
                binaryNum = binaryNum.multiply(mul2);
                addExponent++;
//                System.out.println("ADD E: " + addExponent);
            }
        }

        this.exponent = String.valueOf(Integer.parseInt(exponent) - minusExponent + addExponent);
        return String.valueOf(binaryNum);
    }

    /**
     *
     * @param convertedNum the string to get the mantissa
     */
    public void completeMantissa(String convertedNum){
        String tempMantissa = "";

        if (convertedNum.contains(".")){
            tempMantissa = convertedNum.substring(2);
        }
//        String mantissa = this.mantissa.substring(0, this.mantissa.length()-1);

        if (tempMantissa.length() < 10) {
            while(tempMantissa.length() < 10){
                tempMantissa += "0";
            }
        } else if (tempMantissa.length() > 10) {
            tempMantissa = tempMantissa.substring(0, 10);
        }
        this.mantissa = tempMantissa;
    }

}
