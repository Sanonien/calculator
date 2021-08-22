package com.company;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
//        System.out.println("give me input");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        inputValidator iv = new inputValidator();
        if (!iv.taskPattern(input) || (iv.numeralInText(input)&&iv.romanInText(input))) {
            throw new IllegalArgumentException("Wrong input");
        }
        Counter calc = new Counter();
        int result;
        if (iv.romanInText(input)){
            System.out.println(input);
            result = calc.retRomanRes(input);
            if (result < 1){
                throw new IllegalArgumentException("Roman numeral has to be positive number");
            }
            System.out.println(RomanNumber.toRoman(result));
        } else {
            result = calc.retArabRes(input);
            System.out.println(result);
        }
    }
}

class Counter {

    public static int retArabRes(String a){
        String[] operators = a.split("[0-9]+");
        String[] operands =a.split("[+\\-*/]");
        Arrays.parallelSetAll(operators, (i) -> operators[i].trim());
        Arrays.parallelSetAll(operands, (i) -> operands[i].trim());
        int aggregate = Integer.parseInt(operands[0].trim());
        for(int i=1;i<operands.length;i++){
            if(operators[i].equals("+"))
                aggregate += Integer.parseInt(operands[i].trim());
            else if(operators[i].equals("*"))
                aggregate *= Integer.parseInt(operands[i].trim());
            else if(operators[i].equals("/"))
                aggregate /= Integer.parseInt(operands[i].trim());
            else
                aggregate -= Integer.parseInt(operands[i].trim());
        }
        return aggregate;
    }

    public static int retRomanRes(String a){
        RomanNumber rm = new RomanNumber();

        String[] operators = a.split("[(X|IX|IV|V?I{0,3})]+");
        String[] operands =a.split("[+\\-*/]");
        Arrays.parallelSetAll(operators, (i) -> operators[i].trim());
        Arrays.parallelSetAll(operands, (i) -> operands[i].trim());
        int aggregate = rm.toInt(operands[0].trim());
        for(int i=1;i<operands.length;i++){
            if(operators[i].equals("+"))
                aggregate += rm.toInt(operands[i].trim());
            else if(operators[i].equals("*"))
                aggregate *= rm.toInt(operands[i].trim());
            else if(operators[i].equals("/"))
                aggregate /= rm.toInt(operands[i].trim());
            else
                aggregate -= rm.toInt(operands[i].trim());
        }
        return aggregate;
    }
}

class RomanNumber {
    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();
    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }
    private final static Map<Character, Integer> mapp = new HashMap();
    static {
        mapp.put('I', 1);
        mapp.put('V', 5);
        mapp.put('X', 10);
        mapp.put('L', 50);
        mapp.put('C', 100);
        mapp.put('D', 500);
        mapp.put('M', 1000);
    }

    public static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

    public static int toInt(String s){
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && mapp.get(s.charAt(i)) > mapp.get(s.charAt(i - 1))) {
                result += mapp.get(s.charAt(i)) - 2 * mapp.get(s.charAt(i - 1));
            } else {
                result += mapp.get(s.charAt(i));
            }
        }
        return result;
    }
}

class inputValidator{
    public static boolean numeralInText(String s){
        return Pattern.matches(".*\\d.*", s);
    }
    public static boolean romanInText(String s){
        // Up to 10
        return Pattern.matches(".*[iIVvXx].*", s);
    }
    public static boolean taskPattern(String s){
        return Pattern.matches("^([1-9]|10|(X|IX|IV|V?I{0,3})) ?[+\\-*/] ?([1-9]|10|(X|IX|IV|V?I{0,3}))", s);
    }
}
