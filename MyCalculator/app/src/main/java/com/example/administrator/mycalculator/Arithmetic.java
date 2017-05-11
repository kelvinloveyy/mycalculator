package com.example.administrator.mycalculator;

import java.math.BigDecimal;//用来对超过16位有效位的数进行精确的运算

/**
 * Created by Administrator on 2017/5/4.
 */

public enum Arithmetic {

    ADD, MINUS, MULTIPLY, DIVIDE, SQUARE, ROOT;
    public String Values(String num1, String num2) {
        BigDecimal number1 = new BigDecimal(num1);
        BigDecimal number2 = new BigDecimal(num2);
        BigDecimal number = BigDecimal.valueOf(0);
        double mun;
        switch (this) {
            case ADD:
                number = number1.add(number2);
                break;
            case MINUS:
                number = number1.subtract(number2);
                break;
            case MULTIPLY:
                number = number1.multiply(number2);
                break;
            case DIVIDE:
                number = number1.divide(number2,12,BigDecimal.ROUND_UP);
                break;
            case SQUARE:
                number = number1.pow(number2.intValue());
                break;
            case ROOT:
                mun = java.lang.StrictMath.pow(number1.intValue(),1.0/number2.intValue());
                number= new BigDecimal(mun);
                break;

        }
        return number.stripTrailingZeros().toPlainString(); //移除所有尾部零,并以String形式输出
        //如果是直接toString 就以科学计数输出

    }
    }












