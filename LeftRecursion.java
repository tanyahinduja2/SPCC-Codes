import java.util.*;

class LeftRecursion {
    public static void main(String args[]) {
        int nt, n, i, j;
        int flag = 0;
        char a;
        String output = new String();
        String str1 = "->";
        String str3 = "@";
        String str2 = new String();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of Non-Terminals (Number of productions): ");
        nt = sc.nextInt();
        char[] nonterm = new char[nt];

        System.out.println("Enter the non-terminals: ");
        for (i = 0; i < nt; i++)
            nonterm[i] = sc.next().charAt(0);
        String[] prod = new String[nt]; 
        System.out.println("Enter the productions(Use @ for epsilon): ");
        String temp = sc.nextLine();

        for (i = 0; i < nt; i++)
            prod[i] = sc.nextLine();
        for (i = 0; i < nt; i++)
        {
            flag = 0;
            if (prod[i].length() > 3)
                temp = prod[i].substring(3); 

            String arr1[] = temp.split("\\|"); 
            a = prod[i].charAt(0); 
            str2 = a + "'"; 

            for (j = 0; j < arr1.length; j++) 
            {
                if (flag == 0) //
                {
                    if (a == arr1[j].charAt(0)) 
                    {
                        flag = 1;
                        break; 
                    }
                }
            }
            if (flag == 1) {
                for (j = 0; j < arr1.length; j++) 
                {
                    if (a == arr1[j].charAt(0)) 
                        output += "\n" + str2 + str1 + arr1[j].substring(1) + str2;
                    else if (arr1[j].equals(str3))
                        output += "\n" + a + str1 + str2;
                    else
                        output += "\n" + a + str1 + arr1[j] + str2; 
                }
            }
            if (flag == 1)
                output += "\n" + str2 + str1 + "@";

        }
        System.out.println("Productions After Removal of Left Recursion: \n" + output);
    }
}
