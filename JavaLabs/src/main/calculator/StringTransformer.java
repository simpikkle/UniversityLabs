package main.calculator;

import main.LabException;

import java.util.Stack;

public class StringTransformer {

    private Stack<Character> charStack = new Stack<>();
    private String infix = "";
    private String postfix = "";

    public StringTransformer() {
    }

    public StringTransformer(String infix) {
        this.infix = infix;
    }

    public StringTransformer withInfix(String infix) {
        this.infix = infix;
        return this;
    }

    public StringTransformer withPostfix(String postfix) {
        this.postfix = postfix;
        return this;
    }

    public String toPostfix() throws LabException {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            switch (c) {
                case '+':
                case '-':
                case '*':
                case '/':
                    number = addToPostfix(number);
                    getOperator(c);
                    break;
                case '(':
                    number = addToPostfix(number);
                    charStack.push(c);
                    break;
                case ')':
                    number = addToPostfix(number);
                    getBrackets();
                    break;
                default:
                    if (Character.isDigit(c) || c == '.') {
                        number.append(c);
                        break;
                    } else {
                        throw new LabException("Unexpected character: " + c);
                    }
            }
        }
        addToPostfix(number);
        while(!charStack.isEmpty()) {
            postfix += charStack.pop();
        }
        return postfix;
    }

    private StringBuilder addToPostfix(StringBuilder number) {
        if (!number.toString().equals("")) {
            postfix += number + ";";
        }
        return new StringBuilder();
    }

    private void getBrackets() {
        while(!charStack.isEmpty()) {
            char c = charStack.pop();
            if (c != '(') {
                postfix += c;
            }
            else {
                break;
            }
        }
    }

    private void getOperator(char operator) {
        while(!charStack.isEmpty()) {
            char previousOperator = charStack.pop();
            int currentPriority = getPriority(operator);
            int previousPriority = getPriority(previousOperator);
            if (previousOperator != '(') {
                if (currentPriority > previousPriority) {
                    charStack.push(previousOperator);
                    break;
                }
                else {
                    postfix += previousOperator;
                }
            }
            else {
                break;
            }
        }
        charStack.push(operator);
    }

    private int getPriority(char operator) {
        return operator == '+' || operator == '-' ? 1 : 2;
    }
}
