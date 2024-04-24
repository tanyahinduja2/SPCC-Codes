import java.util.Stack;

public class ThreeAddressCodeGenerator {
    
    // Function to generate three-address code from an infix expression
    public static String generateThreeAddressCode(String expression) {
        StringBuilder threeAddressCode = new StringBuilder();
        Stack<Character> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        
        for (char c : expression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                operands.push(Character.toString(c));
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    char op = operators.pop();
                    String operand2 = operands.pop();
                    String operand1 = operands.pop();
                    String temp = "t" + (operands.size() + 1);
                    operands.push(temp);
                    threeAddressCode.append(temp).append(" = ").append(operand1).append(" ").append(op).append(" ").append(operand2).append("\n");
                }
                operators.pop(); // Pop the '('
            } else { // Operator
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    char op = operators.pop();
                    String operand2 = operands.pop();
                    String operand1 = operands.pop();
                    String temp = "t" + (operands.size() + 1);
                    operands.push(temp);
                    threeAddressCode.append(temp).append(" = ").append(operand1).append(" ").append(op).append(" ").append(operand2).append("\n");
                }
                operators.push(c);
            }
        }
        
        while (!operators.isEmpty()) {
            char op = operators.pop();
            String operand2 = operands.pop();
            String operand1 = operands.pop();
            String temp = "t" + (operands.size() + 1);
            operands.push(temp);
            threeAddressCode.append(temp).append(" = ").append(operand1).append(" ").append(op).append(" ").append(operand2).append("\n");
        }
        
        return threeAddressCode.toString();
    }
    
    // Function to determine precedence of operators
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
    
    public static void main(String[] args) {
        String expression = "a*(b+c)-d/e";
        System.out.println("Expression: " + expression);
        String threeAddressCode = generateThreeAddressCode(expression);
        System.out.println("Three Address Code:");
        System.out.println(threeAddressCode);
    }
}
