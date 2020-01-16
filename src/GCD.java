public class GCD {
    //greatest common divisor
    public static void main(String[] args) {
        int first = 21;
        int second = 27;
        System.out.println(gcd(first, second));
    }

    private static int gcd(int x, int y) {
        //x is the denominator
        //y is the divisor
        //21, 27,
        //27, 21,
        //21, 6
        //6, 3
        //3, 0

        if (y == 0) {
            return x;
        }
        return gcd(y, x % y);
    }
}
