public class Entended_Euclidean_Algorithms {

    //given integer a, b, there must be a pair of (x, y) to make ax + by = gcd(a, b);
    public static void main(String[] args) {
        int first = 27;
        int second = 107;
        int[] res = getInverse(first, second);
        //System.out.println(res[0] + " " + res[1]);
    }

    private static int[] getInverse(int a, int b) {
        //a = 27, b = 107, find x to make ax % b = 1;
        //107 = 27 * 3 + 26
        //27 = 26 * 1 + 1
        //26 = 1 * 26 + 0

        //1 = 27 - 26
        //1 = 27 - (107 - 27 * 3) = -107 + 4 * 27;
        //return 4;
        if (b == 0) {
            System.out.println("exit" + a + " " + b);
            return new int[]{1, 0};
        }
        int[] temp = getInverse(b, a % b);
        System.out.println(a + " " + b + " " + temp[0] + " " + temp[1]);
        return new int[]{temp[1], temp[0] - (a / b) * temp[1]};
    }


    public  static long[] getInvArray(int n, int p){
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = (p - p / i) * inv[p % i] % p;
        }

        return inv;
    }
}
