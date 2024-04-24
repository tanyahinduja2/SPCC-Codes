import java.util.*;

public class FirstFollow {
    public static void main(String[] args) {
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"A", "BC"});
        data.add(new String[]{"B", "Ax|x"});
        data.add(new String[]{"C", "yC|y"});

        Map<String, Set<Character>> first = new HashMap<>();
        Map<String, Set<Character>> follow = new HashMap<>();

        // Calculate First
        for (int i = data.size() - 1; i >= 0; i--) {
            String left = data.get(i)[0];
            String[] right = data.get(i)[1].split("\\|");

            Set<Character> tempFirst = new HashSet<>();

            for (String prod : right) {
                for (int j = 0; j < prod.length(); j++) {
                    char symbol = prod.charAt(j);
                    if (Character.isLowerCase(symbol) || symbol == 'è') {
                        tempFirst.add(symbol);
                        break;
                    } else {
                        Set<Character> firstSet = first.get(String.valueOf(symbol));
                        if (firstSet == null) {
                            firstSet = new HashSet<>();
                            first.put(String.valueOf(symbol), firstSet);
                        }
                        if (!firstSet.contains('è')) {
                            tempFirst.addAll(firstSet);
                            break;
                        } else {
                            tempFirst.addAll(firstSet);
                            if (j == prod.length() - 1) {
                                tempFirst.add('è');
                            }
                        }
                    }
                }
            }
            first.put(left, tempFirst);
        }

        System.out.println("First:");
        System.out.println(first);

        // Calculate Follow
        for (String[] production : data) {
            String left = production[0];
            Set<Character> tempFollow = new HashSet<>();

            if (production.equals(data.get(0))) {
                tempFollow.add('$');
            }

            for (String[] prod : data) {
                for (String right : prod[1].split("\\|")) {
                    int index = right.indexOf(left);

                    if (index == -1) {
                        continue;
                    }

                    // NT at the end
                    if (index == right.length() - 1) {
                        if (!left.equals(prod[0])) {
                            tempFollow.addAll(follow.get(prod[0]));
                        }
                    }
                    // T after NT in follow
                    else if (Character.isLowerCase(right.charAt(index + 1))) {
                        tempFollow.add(right.charAt(index + 1));
                    }
                    // NT after NT in follow
                    else {
                        int nextIndex = index + 1;
                        while (nextIndex < right.length()) {
                            char nextSymbol = right.charAt(nextIndex);
                            if (Character.isLowerCase(nextSymbol)) {
                                tempFollow.add(nextSymbol);
                                break;
                            } else {
                                tempFollow.addAll(first.get(String.valueOf(nextSymbol)));
                                if (!first.get(String.valueOf(nextSymbol)).contains('è')) {
                                    break;
                                }
                                nextIndex++;
                            }
                        }
                        if (nextIndex == right.length()) {
                            if (!left.equals(prod[0])) {
                                tempFollow.addAll(follow.get(prod[0]));
                            }
                        }
                    }
                }
            }
            follow.put(left, tempFollow);
        }

        System.out.println("Follow:");
        System.out.println(follow);
    }
}
