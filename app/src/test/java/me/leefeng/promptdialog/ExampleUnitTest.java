package me.leefeng.promptdialog;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        System.out.print("asdbja\n");
//        boolean pins[][] = new boolean[4][];
//        for (int i = 0; i < pins.length; i++) pins[i] = new boolean[i + 1];
//        for (int i = 0; i < pins.length; i++) {
//            for (int k = 0; k < pins[i].length; k++) System.out.print(pins[i][k] + " ");
//            System.out.println("");
//        }
        test18_2();

    }

    static int x = 3, y = 4;

    static int p(int a, int b) {
        int w = a + b;
        a = x + y + b;
        b = x + y + w;
        System.out.println("w=" + w + " x=" + x + " y=" + y + " a=" + a + " b=" + b);
        return b;
    }

    public  void test18_2() {
        int u = 5, v = 6;
        u = p(u, v) + v;
        System.out.println("u=" + u + " v=" + v + " x=" + x + " y=" + y);
    }

//    static int x = 4, y = 5;
//
//    static void p(int a, int b) {
//        int x = 6;
//        a = x + y + b;
//        b = x + y + a;
//        System.out.println("\tx=" + x + " y=" + y + "\ta=" + a + " b=" + b);
//    }

    public void text18() {
        int u = 2, v = 3;
        p(u, v);
        System.out.println("\tu=" + u + " v=" + v + "\tx=" + x + " y=" + y);
    }

    void text13() {
        int i, n, k, j;
        int a[] = new int[9];
        a[0] = n = 2;
        i = 1;
        while (i < a.length) {
            n += 1;
            j = (int) Math.sqrt(n);
            for (k = 2; k <= j; k++) if (n % k == 0) break;
            if (i == j) {
                a[i] = n;
                System.out.println(i + " " + a[i]);
                i++;
            }
        }
        n = 0;
//        for (k = 0; k <){
//           int  number = Integer.parseInt(args[0]);
//            System.out.println("the square root of " + number + "is " + Math.sqrt(number));
//        }
    }

}
//test13
//false
//false false
//false false false
//false false false false

//1 3
//2 4
//3 9
//4 16
//5 25
//6 36
//7 49
//8 64

//test18
//  x=6 y=5	a=14 b=25
//  u=2 v=3	x=4 y=5
//w=11 x=3 y=4 a=13 b=18
//u=24 v=6 x=3 y=4