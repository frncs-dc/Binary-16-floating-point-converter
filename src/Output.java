public class Output {

    //attributes
    public String sign;
    public String exponent;
    public String mantissa;

    //util
    private final int bias = 15;

    public void extractValues(String binaryNum){
        //-1.101 x 2^something

        String sign, mantissa, exponent;
        int firstInstanceofDecimalPoint = binaryNum.indexOf(".");
        int firstInstanceofExp = binaryNum.indexOf("^");
        int firstInstanceofx = binaryNum.indexOf("x");

        if (binaryNum.contains(".")){
            //when there is a decimal point present
            this.sign = String.valueOf(isNegative(binaryNum) ? 1 : 0); //if what is returned is 1, then its negative else positive

            this.mantissa = binaryNum.substring(firstInstanceofDecimalPoint+1, firstInstanceofx); //gets everything after the decimal point until x2^stuff

            this.exponent = binaryNum.substring(firstInstanceofExp+1); //gets everything after the ^ sign

            System.out.println("Sign: " +this.sign+" Exponent: "+this.exponent+" Mantissa: "+this.mantissa);
        } else {
            //-101 x 2^something
            //if there is no decimal point
            this.sign = String.valueOf(isNegative(binaryNum) ? 1 : 0); //if what is returned is 1, then its negative else positive

            this.mantissa = binaryNum.substring(1, firstInstanceofx); //starts reading after sign/no sign then stops reading before 'x'

            this.exponent = binaryNum.substring(firstInstanceofExp+1); //gets everything after the ^ sign

            System.out.println("Sign: " + this.sign +" Exponent: "+this.exponent+" Mantissa: "+this.mantissa);
        }
        
    }

    public boolean isNegative(String binaryNum){
        return binaryNum.startsWith("-");
    }
    private String computeBias(){
        int exponentDecimal = Integer.parseInt(exponent) + bias;
        String exponentBinary = "";

        for (int count = 0; count < 5; count++){
            if(exponentDecimal % 2 == 1){
                exponentBinary = "1" + exponentBinary;
            }
            else{
                exponentBinary = "0" + exponentBinary;
            }
            exponentDecimal /= 2;
        }

        return exponentBinary;
    }

    public String completeMantissa(){
        String mantissa = this.mantissa.substring(0, this.mantissa.length()-1);

        while(mantissa.length() < 10){
            mantissa += "0";
        }
        return mantissa;
    }
    public String getBinaryOutput(){
        String output = "";
        output += sign + " ";
        output += computeBias() + " ";
        output += completeMantissa();

        return output;
    }
}
