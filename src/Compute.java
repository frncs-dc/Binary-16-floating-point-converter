import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.abs;
/*
Input: integer binary number non 1f form

 */
public class Compute {
    //Original
    private float binaryNum;
    //Parts of the floating point
    private int signBit;
    private String exponent;
    private String mantissa;
    //Utils
    private double form1f;
    private int exponentCount = 0;
    private final int bias = 15;
    Compute(float binaryNum){
        this.binaryNum  = binaryNum;
        //get sign bit
        this.signBit = binaryNum < 0 ? 1 : 0;
        //Convert to 1.f
        this.form1f = convertTo1f(abs(binaryNum));
        this.exponent = computeBias();
        this.mantissa = computeMantissa(form1f);
        //signBit = substr 1
        //exponent = substr 2-6
        //mantissa = substr 7-16
    }

    private double convertTo1f(float unconvertedNum){
        double binaryNum = unconvertedNum;
        if(binaryNum < 2 && binaryNum > 0){
            return binaryNum;
        }
        // binaryNum 0.XX
        else if (binaryNum < 2){
            while(binaryNum < 2){
                binaryNum *= 10;
                exponentCount--;
            }
        }
        //binaryNum 1XX
        else if (binaryNum > 2){
            while(binaryNum > 2){
                binaryNum *= 0.1;
                exponentCount++;
            }
        }
        return binaryNum;
    }

    private String computeBias(){
        int exponentDecimal = exponentCount + bias;
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
//        while(exponentDecimal > 0){
//            if(exponentDecimal % 2 == 1){
//                exponentBinary = exponentBinary * 10 + 1;
//            }
//            else{
//                exponentBinary = exponentBinary * 10;
//            }
//
//            exponentDecimal /= 2;
//        }
        return exponentBinary;
    }
    private String computeMantissa(double form1f){
        double decimals = form1f - (int)form1f;
        String mantissa = "";

        for(int count = 0; count <10; count++){
            decimals *= 10;
            int whole = (int)decimals;
            mantissa = mantissa + whole;
            decimals -= whole;
        }
        return mantissa;
    }

    public String getFullOutput(){
        String fullOutput = "";
        fullOutput += signBit + " ";
        fullOutput += exponent + " ";
        fullOutput += mantissa;
        return fullOutput;
    }

}
