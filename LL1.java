import java.util.ArrayList;
import java.util.HashMap;

public class LL1ParsingTable {
    public static void main(String[] args) {
        ArrayList<String[]> data = new ArrayList<>();
        data.add(new String[]{"E", new String[]{"TA"}});
        data.add(new String[]{"A", new String[]{"aTA", "è"}});
        data.add(new String[]{"T", new String[]{"FB"}});
        data.add(new String[]{"B", new String[]{"bFB", "è"}});
        data.add(new String[]{"F", new String[]{"c", "dEf"}});
        
        HashMap<String, ArrayList<String>> first = new HashMap<>();
        first.put("TA", new ArrayList<String>() {{
            add("d");
            add("c");
        }});
        first.put("aTA", new ArrayList<String>() {{
            add("a");
        }});
        first.put("FB", new ArrayList<String>() {{
            add("c");
            add("d");
        }});
        first.put("bFB", new ArrayList<String>() {{
            add("b");
        }});
        first.put("c", new ArrayList<String>() {{
            add("c");
        }});
        first.put("dEf", new ArrayList<String>() {{
            add("d");
        }});

        HashMap<String, ArrayList<String>> follow = new HashMap<>();
        follow.put("E", new ArrayList<String>() {{
            add("$");
        }});
        follow.put("A", new ArrayList<String>() {{
            add("$");
            add("f");
        }});
        follow.put("T", new ArrayList<String>() {{
            add("a");
            add("$");
            add("f");
        }});
        follow.put("B", new ArrayList<String>() {{
            add("a");
            add("$");
            add("f");
        }});
        follow.put("F", new ArrayList<String>() {{
            add("b");
            add("a");
            add("$");
            add("f");
        }});

        ArrayList<String> terminals = new ArrayList<>();
        ArrayList<String> nonTerminals = new ArrayList<>();

        // Extract terminals and non-terminals
        for (String[] production : data) {
            nonTerminals.add(production[0]);
            for (String rhs : production[1]) {
                for (char c : rhs.toCharArray()) {
                    if (Character.isLowerCase(c) && c != 'è' && !terminals.contains(Character.toString(c))) {
                        terminals.add(Character.toString(c));
                    }
                }
            }
        }
        terminals.add("$");

        System.out.println("Terminals : " + String.join(" ", terminals));
        System.out.println("\nFirst :");
        for (String key : first.keySet()) {
            System.out.println(key + " -> " + String.join(", ", first.get(key)));
        }
        System.out.println("\nFollow :");
        for (String key : follow.keySet()) {
            System.out.println(key + " -> " + String.join(", ", follow.get(key)));
        }

        HashMap<String, HashMap<String, ArrayList<String>>> table = new HashMap<>();

        for (String nonTerm : nonTerminals) {
            table.put(nonTerm, new HashMap<>());
            for (String term : terminals) {
                table.get(nonTerm).put(term, new ArrayList<>());
            }
        }

        for (String[] production : data) {
            String left = production[0];
            String[] right = production[1];

            for (String rhs : right) {
                if (rhs.equals("è")) {
                    for (String itr : follow.get(left)) {
                        table.get(left).get(itr).add(left + "->" + rhs);
                    }
                } else {
                    for (String itr : first.get(rhs)) {
                        table.get(left).get(itr).add(left + "->" + rhs);
                    }
                }
            }
        }

        System.out.println("\nParsing Table :");
        System.out.print("\t");
        for (String terminal : terminals) {
            System.out.print(terminal + "\t");
        }
        System.out.println();

        for (String nonTerm : nonTerminals) {
            System.out.print(nonTerm + "\t");
            for (String terminal : terminals) {
                ArrayList<String> productions = table.get(nonTerm).get(terminal);
                if (!productions.isEmpty()) {
                    System.out.print(String.join(" ", productions) + "\t");
                } else {
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
}
