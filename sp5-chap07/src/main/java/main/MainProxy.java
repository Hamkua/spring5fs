package main;


import chap07.ExeTimeCalculator;
import chap07.ImpeCalculator;
import chap07.RecCalculator;

public class MainProxy {

    public static void main(String[] args) {
        ExeTimeCalculator ttCal1 = new ExeTimeCalculator(new ImpeCalculator());
        System.out.println(ttCal1.factorial(20));

        ExeTimeCalculator ttCal2 = new ExeTimeCalculator(new RecCalculator());
        System.out.println(ttCal2.factorial(20));

//        ImpeCalculator.factorial(20) 실행 시간 = 33834
//        2432902008176640000
//        RecCalculator.factorial(20) 실행 시간 = 3000
//        2432902008176640000

    }
}
