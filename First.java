import java.util.*;

public class First {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rules in the grammar : ");
        int num = sc.nextInt();
        String rules[] = new String[num];
        sc.nextLine();
        System.out.println("Enter the grammar rules (in the format NonTerminal->Production1|Production2|...):");
        for (int i = 0; i < num; i++) {
            rules[i] = sc.nextLine();
        }
        Map<Character, List<String>> grammar = parseGrammar(rules);
        Map<Character, Set<Character>> firstSets = computeFirstSets(grammar);
        System.out.println("First sets:");
        for (Map.Entry<Character, Set<Character>> entry : firstSets.entrySet()) {
            System.out.println("First(" + entry.getKey() + ") = " + entry.getValue());
        }
        sc.close();
    }

    public static Map<Character, List<String>> parseGrammar(String rules[]) {
        Map<Character, List<String>> grammar = new HashMap<>();
        for (String rule : rules) {
            String parts[] = rule.split("->");
            char nonTerminal = parts[0].charAt(0);
            String productions[] = parts[1].split("\\|");
            grammar.put(nonTerminal, Arrays.asList(productions));
        }
        return grammar;
    }

    public static Map<Character, Set<Character>> computeFirstSets(Map<Character, List<String>> grammar) {
        Map<Character, Set<Character>> firstSets = new HashMap<>();
        Set<Character> calculated = new HashSet<>();
        for (Character nonTerminal : grammar.keySet()) {
            calculateFirst(grammar, firstSets, nonTerminal, calculated);
        }
        return firstSets;
    }

    public static void calculateFirst(Map<Character, List<String>> grammar, Map<Character, Set<Character>> firstSets, char nonTerminal, Set<Character> calculated) {
        if (firstSets.containsKey(nonTerminal) || calculated.contains(nonTerminal)) {
            return;
        }
        calculated.add(nonTerminal);
        Set<Character> firstSet = new HashSet<>();
        for (String production : grammar.get(nonTerminal)) {
            if (production.length() == 1) {
                char symbol = production.charAt(0);
                if (symbol == '@' || Character.isLowerCase(symbol)) {
                    firstSet.add(symbol);
                }
            } else if (production.length() > 1) {
                char symbol = production.charAt(0);
                if (Character.isLowerCase(symbol)) {
                    firstSet.add(symbol);
                } else if (Character.isUpperCase(symbol)) {
                    calculateFirst(grammar, firstSets, symbol, calculated);
                    Set<Character> symbolFirstSet = firstSets.getOrDefault(symbol, new HashSet<>());
                    firstSet.addAll(symbolFirstSet);
                }
            }
        }
        firstSets.put(nonTerminal, firstSet);
    }
}
