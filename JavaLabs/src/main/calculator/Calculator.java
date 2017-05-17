package main.calculator;

import main.LabException;

import java.util.Stack;

public class Calculator {

    private Stack<Double> doubleStack = new Stack<>();
    private String infix = "";
    private String postfix = "";

    public static Calculator fromInfix(String infix) {
        return new Calculator().withInfix(infix);
    }

    public Calculator withInfix(String infix) {
        this.infix = infix;
        return this;
    }

    public Calculator withPostfix(String postfix) {
        this.postfix = postfix;
        return this;
    }

    public String calculate() throws LabException {
        StringTransformer transformer = new StringTransformer().withInfix(infix);
        postfix = transformer.toPostfix();
        String number = "";
        double result;
        char executedChar = '0';
        for (int i = 0; i < postfix.length(); i++) {
            char c = postfix.charAt(i);
            if (Character.isDigit(c) || c == '.') {
                number += c;
            } else if (c == ';') {
                doubleStack.push(Double.parseDouble(number));
                number = "";
            }
            else {
                double secondOperand = doubleStack.pop();
                double firstOperand = doubleStack.pop();
                if (executedChar == '0') {
                    executedChar = c;
                } else {
                    executedChar = '1';
                }
                switch (c) {
                    case '+': result = firstOperand + secondOperand;
                        break;
                    case '-': result = firstOperand - secondOperand;
                        break;
                    case '*': result = firstOperand * secondOperand;
                        break;
                    case '/': result = firstOperand / secondOperand;
                        break;
                    default:
                        result = 0;
                }
                doubleStack.push(result);
            }
        }
        result = doubleStack.pop();
        String operation = getOperationByChar(executedChar);
        return operation + ((result * 100 % 100 == 0)? Integer.toString((int) result) : Double.toString(result));
    }

    private String getOperationByChar(char executedChar) {
        switch (executedChar) {
            case '+': return "Sum: ";
            case '-': return "Subtraction: ";
            case '*': return "Multiplication: ";
            case '/': return "Division: ";
            default: return "Result: ";
        }
    }
}
