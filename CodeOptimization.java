import java.util.*;

class CodeOptimization{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the number of productions");
		int n = sc.nextInt();
		sc.nextLine();
		
		String arr[] = new String[n];
		Set <String> hs = new LinkedHashSet <> ();
		
		arr[0] = "t1 = -c";
		arr[1] = "t2 = a + b";
		arr[2] = "t3 = a + b";
		arr[3] = "t4 = a + b";
		arr[4] = "t5 = d + e";
		arr[5] = "t6 = a + b";
		arr[6] = "t7 = -c";
		arr[7] = "t8 = d + e";
		arr[8] = "t9 = 4 * t4";
		
		/*for(int i=0; i<n; i++){
			System.out.println("Enter production no " + (i+1));
			arr[i] = sc.nextLine();
		}*/
		
		for(int i=0; i<n; i++){
			String rhs = arr[i].split("=")[1];
			hs.add(rhs);
		}
		
		int count = 1;
		
		for(String i : hs){
			String s = "t"+count+" ="+i;
			System.out.println(s);
			count++;
		}
		sc.close();
	}
}
