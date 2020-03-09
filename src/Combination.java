class Combination {

    private static long combo(int n, int m) {
        int mod = (int) (1e9 + 7);
        long[] inv = getInvArray(m, mod);
        long res = 1;
        for (int i = 1; i <= m; i++) {
            res = (res * (n - i + 1) % mod) * inv[i] % mod;
        }
        return res;
    }

    public static long[] getInvArray(int n, int mod) {
        long[] inv = new long[n + 1];
        inv[1] = 1;
        for (int i = 2; i <= n; i++) {
            inv[i] = (mod - mod / i) * inv[mod % i] % mod;
        }

        return inv;
    }
}