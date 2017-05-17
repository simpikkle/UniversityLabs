package main.labs;

import main.LabException;
import main.calculator.Calculator;

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
}
