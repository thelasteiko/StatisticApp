package working;

import java.util.Scanner;

public class tester {

    public static void main(String[] args) {
        arrays();
    }

    public static void formatoutput() {
        String str = "java";
        int n = 9;
        System.out.printf("%-15s%03d", str, n);
    }

    public static void arrays() {
        int t, a, b, n, last;
        Scanner sc = new Scanner(System.in);
        t = sc.nextInt();

        while (t > 0) {
            a = sc.nextInt();
            b = sc.nextInt();
            n = sc.nextInt();
            last = a + b;
            System.out.print(last + " ");
            for (int i = 1; i < n; i++) {
                last += Math.pow(2, i) * b;
                if (i == n - 1) System.out.println(last);
                else System.out.print(last + " ");
            }
            t--;
        }
        sc.close();
    }

}
