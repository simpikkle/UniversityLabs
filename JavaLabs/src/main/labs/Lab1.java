package main.labs;

import main.LabException;
import main.calculator.Calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lab1 {

    private String inputString;

    public Lab1(String inputString) {
        this.inputString = inputString;
    }

    public String calculate() {
        if (inputString.isEmpty()) {
            return "Please, input the expression!";
        }
        try {
            return Calculator.fromInfix(inputString).calculate();
        } catch (LabException e) {
            return e.getMessage();
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter the expression: ");
        System.out.flush();
        Lab1 lab1 = new Lab1(getString());
        System.out.println(lab1.calculate());
    }

    private static String getString() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String s = "";
        try {
            s = reader.readLine();
        }
        catch(IOException e) {
            System.err.print(e.toString());
        }
        return s;
    }
}
