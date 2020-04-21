import java.io.*;
import java.util.*;
import java.lang.*;


public class Main implements Runnable
{
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);
        int n = in.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextInt();
        }
        getRes(arr, w);

        w.flush();
        w.close();
    }

    private static void getRes(int[] arr, PrintWriter w) {
        // System.out.println("Start!");
        int l = arr.length;
        // graph[i][j] is the length of that edge.
        long[][] graph = new long[l][l];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if (i == j) continue;
                graph[i][j] = (long)arr[i] * Math.abs(i - j);
            }
        }

        long res = 0;
        boolean[] visx = new boolean[l];
        boolean[] visy = new boolean[l];
        long[] lx = new long[l], ly = new long[l];
        int[] match = new int[l];

        // find the maximum match for each node, no matter there is duplicate or not.
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if (lx[i] < graph[i][j]) {
                    lx[i] = graph[i][j];
                }
            }
        }

        // initiate the match array
        Arrays.fill(match, -1);

        // for each point for the first part
        for (int i = 0; i < l; i++) {
            while (true) {
                Arrays.fill(visx, false);
                Arrays.fill(visy, false);
                if (find(graph, match, visx, visy, lx, ly, i)) break;

                long d = Long.MAX_VALUE;
                for (int j = 0; j < l; j++) {
                    if (visx[j]) {
                        for (int k = 0; k < l; k++) {
                            if (!visy[k]) {
                                d = Math.min(d, lx[j] + ly[k] - graph[j][k]);
                            }
                        }
                    }
                }
                if (d == Integer.MAX_VALUE) {
                    System.out.println("getres " + res);
                    return;
                }
                for (int j = 0; j < l; j++) {
                    if (visx[j]) {
                        lx[j] -= d;
                    }
                }
                for (int j = 0; j < l; j++) {
                    if (visy[j]) {
                        ly[j] += d;
                    }
                }
            }
        }

        // System.out.println("finish while");
        for (int i = 0; i < l; i++) {
            if (match[i] != - 1) {
                res += graph[match[i]][i];
            }
        }


        w.println(res);
    }

    private static boolean find(long[][] g, int[] match, boolean[] visx, boolean[] visy, long[] lx, long[] ly, int i) {
        // System.out.println("find bug");
        visx[i] = true;
        int l = g.length;
        for (int j = 0; j < l; j++) {
            if (!visy[j] && lx[i] + ly[j] == g[i][j]) {
                visy[j] = true;
                if (match[j] == -1 || find(g, match, visx, visy, lx, ly, match[j])) {
                    match[j] = i;
                    return true;
                }
            }
        }
        return false;
    }



    static class InputReader
    {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private SpaceCharFilter filter;
        private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        public InputReader(InputStream stream)
        {
            this.stream = stream;
        }

        public int read()
        {
            if (numChars==-1)
                throw new InputMismatchException();

            if (curChar >= numChars)
            {
                curChar = 0;
                try
                {
                    numChars = stream.read(buf);
                }
                catch (IOException e)
                {
                    throw new InputMismatchException();
                }

                if(numChars <= 0)
                    return -1;
            }
            return buf[curChar++];
        }

        public String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
        public int nextInt()
        {
            int c = read();

            while(isSpaceChar(c))
                c = read();

            int sgn = 1;

            if (c == '-')
            {
                sgn = -1;
                c = read();
            }

            int res = 0;
            do
            {
                if(c<'0'||c>'9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));

            return res * sgn;
        }

        public long nextLong()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            long res = 0;

            do
            {
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            while (!isSpaceChar(c));
            return res * sgn;
        }

        public double nextDouble()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            int sgn = 1;
            if (c == '-')
            {
                sgn = -1;
                c = read();
            }
            double res = 0;
            while (!isSpaceChar(c) && c != '.')
            {
                if (c == 'e' || c == 'E')
                    return res * Math.pow(10, nextInt());
                if (c < '0' || c > '9')
                    throw new InputMismatchException();
                res *= 10;
                res += c - '0';
                c = read();
            }
            if (c == '.')
            {
                c = read();
                double m = 1;
                while (!isSpaceChar(c))
                {
                    if (c == 'e' || c == 'E')
                        return res * Math.pow(10, nextInt());
                    if (c < '0' || c > '9')
                        throw new InputMismatchException();
                    m /= 10;
                    res += (c - '0') * m;
                    c = read();
                }
            }
            return res * sgn;
        }

        public String readString()
        {
            int c = read();
            while (isSpaceChar(c))
                c = read();
            StringBuilder res = new StringBuilder();
            do
            {
                res.appendCodePoint(c);
                c = read();
            }
            while (!isSpaceChar(c));

            return res.toString();
        }

        public boolean isSpaceChar(int c)
        {
            if (filter != null)
                return filter.isSpaceChar(c);
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next()
        {
            return readString();
        }

        public interface SpaceCharFilter
        {
            public boolean isSpaceChar(int ch);
        }
    }

    public static void main(String args[]) throws Exception
    {
        new Thread(null, new Main(),"Main",1<<27).start();
    }

}
