import java.math.BigDecimal;

public class Output {

    //attributes
    public String sign;
    public String exponent;
    public String mantissa;
    public String exponentBias;

    //util
    private final int bias = 15;

    public boolean isSpecialCase (String binaryInput){
        String[] exp = binaryInput.split("x2\\^");
        for (String a : exp){
            System.out.println(a);
        }
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

    public String expandDecimal(String input){
        String[] expandedNumber = input.split("[x^]");
        double number = Double.parseDouble(expandedNumber[0]);
        int base = Integer.parseInt(expandedNumber[1]);
        int exp = Integer.parseInt(expandedNumber[2]);
        this.exponent = "0";

        return String.valueOf(number * Math.pow(base, exp));
    }

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

//        String exponentBinary = "";
//
//        for (int count = 0; count < 5; count++){
//            if(exponentDecimal % 2 == 1){
//                exponentBinary = "1" + exponentBinary;
//            }
//            else{
//                exponentBinary = "0" + exponentBinary;
//            }
//            exponentDecimal /= 2;
//        }

//        return exponentBinary;
    }

    public String convertTo1f(String unconvertedNum){
        BigDecimal binaryNum = new BigDecimal(unconvertedNum);
        BigDecimal mul1 = new BigDecimal("10");
        BigDecimal mul2 = new BigDecimal("0.1");
        BigDecimal two = new BigDecimal("2");
        BigDecimal zero = new BigDecimal("0");
        int minusExponent = 0;
        int addExponent = 0;
        if( (binaryNum.compareTo(two) < 0 && binaryNum.compareTo(zero) > 0) || binaryNum.compareTo(zero) == 0){
            return String.valueOf(binaryNum);
        }
        // binaryNum 0.XX
        else if (binaryNum.compareTo(two) < 0){
            while(binaryNum.compareTo(two) < 0){
                binaryNum = binaryNum.multiply(mul1);
                minusExponent++;
            }
        }
        //binaryNum 1XX
        else if (binaryNum.compareTo(two) > 0){
            while(binaryNum.compareTo(two) > 0){
                binaryNum = binaryNum.multiply(mul2);
                addExponent++;
            }
        }

        this.exponent = String.valueOf(Integer.parseInt(exponent) - minusExponent + addExponent);
        return String.valueOf(binaryNum);
    }

    public void completeMantissa(String convertedNum){
        String tempMantissa = "";

        if (convertedNum.contains(".")){
            tempMantissa = convertedNum.substring(2);
        }
//        String mantissa = this.mantissa.substring(0, this.mantissa.length()-1);

        while(tempMantissa.length() < 10){
            tempMantissa += "0";
        }
        this.mantissa = tempMantissa;
    }
//    public String getBinaryOutput(){
//        String output = "";
//        output += sign + " ";
//        output += exponentBias + " ";
//        output += completeMantissa();
//
//        return output;
//    }


//    public void extractValues(String binaryNum){
//        //-1.101 x 2^something
//
//        String sign, mantissa, exponent;
//        int firstInstanceofDecimalPoint = binaryNum.indexOf(".");
//        int firstInstanceofExp = binaryNum.indexOf("^");
//        int firstInstanceofx = binaryNum.indexOf("x");
//
//        if (binaryNum.contains(".")){
//            //when there is a decimal point present
//            this.sign = String.valueOf(isNegative(binaryNum) ? 1 : 0); //if what is returned is 1, then its negative else positive
//
//            this.mantissa = binaryNum.substring(firstInstanceofDecimalPoint+1, firstInstanceofx); //gets everything after the decimal point until x2^stuff
//
//            this.exponent = binaryNum.substring(firstInstanceofExp+1); //gets everything after the ^ sign
//
//            System.out.println("Sign: " +this.sign+" Exponent: "+this.exponent+" Mantissa: "+this.mantissa);
//        } else {
//            //-101 x 2^something
//            //if there is no decimal point
//            this.sign = String.valueOf(isNegative(binaryNum) ? 1 : 0); //if what is returned is 1, then its negative else positive
//
//            this.mantissa = binaryNum.substring(1, firstInstanceofx); //starts reading after sign/no sign then stops reading before 'x'
//
//            this.exponent = binaryNum.substring(firstInstanceofExp+1); //gets everything after the ^ sign
//
//            System.out.println("Sign: " + this.sign +" Exponent: "+this.exponent+" Mantissa: "+this.mantissa);
//        }
//
//    }

//    public boolean isNegative(String binaryNum){
//        return binaryNum.startsWith("-");
//    }
}
