import java.util.*;

public class LexicalAnalyzer {
    public static void main(String []args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of lines of code:");
        int num = sc.nextInt();
        sc.nextLine();
        String []codeLines = new String[num];
        System.out.println("Enter the lines of code:");
        for(int i = 0; i < num; i++) {
            codeLines[i] = sc.nextLine();
        }
        List<String> keywords = new ArrayList<>();
        List<String> identifiers = new ArrayList<>();
        List<String> operators = new ArrayList<>();
        List<String> numbers = new ArrayList<>();
        List<String> invalids = new ArrayList<>();
        List<String> separators = new ArrayList<>();

        for(String codeLine : codeLines) {
            codeLine = codeLine.trim();
            if(codeLine.endsWith(";")) {
                if(!separators.contains(";")) {
                    separators.add(";");
                }
                codeLine = codeLine.substring(0, codeLine.length() - 1);
            }
            for(String word : codeLine.split(" ")) {
                if(isKeyword(word)) {
                    if(!keywords.contains(word)) {
                        keywords.add(word);
                    }
                } else if(isIdentifier(word)) {
                    if(!identifiers.contains(word)) {
                        identifiers.add(word);
                    }
                } else if(isOperator(word)) {
                    if(!operators.contains(word)) {
                        operators.add(word);
                    }
                } else if(isNumber(word)) {
                    if(!numbers.contains(word)) {
                        numbers.add(word);
                    }
                } else if(isSeparator(word)) {
                    if(!separators.contains(word)) {
                        separators.add(word);
                    }
                } else if(!identifiers.contains(word) && !invalids.contains(word)) {
                    invalids.add(word);
                }
                
            }
        }
        System.out.println();
        System.out.println("Keyword : " + keywords);
        System.out.println("Identifier : " + identifiers);
        System.out.println("Operator : " + operators);
        System.out.println("Separators : " + separators);
        System.out.println("Number Constants : " + numbers);
        System.out.println("Invalid : " + invalids);
        sc.close();
    }
    public static boolean isKeyword(String word) {
        return Set.of("int", "float", "char", "break", "continue", "String", "new").contains(word);
    }
    public static boolean isIdentifier(String word) {
        if(isKeyword(word) || isNumber(word) || isOperator(word) || isSeparator(word)) {
            return false;
        }
        char firstChar = word.charAt(0);
        return Character.isLetter(firstChar);
    }
    public static boolean isOperator(String word) {
        return Set.of("+", "-", "=", "<", ">", "*", "%", "/").contains(word);
    }
    public static boolean isNumber(String word) {
        if (word.isEmpty()) {
            return false;
        }
        boolean decimalPointFound = false;
        for (char c : word.toCharArray()) {
            if (!Character.isDigit(c)) {
                if (c == '.' && !decimalPointFound) {
                    decimalPointFound = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isSeparator(String word) {
        return Set.of(":", ";", ",").contains(word);
    }
}
