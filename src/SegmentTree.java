import java.io.*;
import java.util.*;
import java.lang.*;

// calculate the max sum of subarray in a range.
public class SegmentTree implements Runnable
{
    @Override
    public void run() {
        InputReader in = new InputReader(System.in);
        PrintWriter w = new PrintWriter(System.out);

        int n = in.nextInt(), m = in.nextInt();
        // System.out.println(n + " " + m);
        for (int i = 0; i < n; i++) {
            nums[i + 1] = in.nextInt();
            // System.out.print(nums[i + 1] + " ");
        }
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            int cmd = in.nextInt();
            int x = in.nextInt(), y = in.nextInt();
            if (cmd == 1) {
                w.println(query(1, Math.min(x, y), Math.max(x, y)).max);
            }
            else {
                modify(1, x, y);
            }
        }

        w.flush();
        w.close();
    }


    static int N = 500050;
    static Node[] tr = new Node[4 * N];
    static int[] nums = new int[N];
    static class Node {
        int left, right;
        long max, prefix, suffix, sum;
        public Node(int l, int r, long m, long p, long s, long sum) {
            left = l;
            right = r;
            max = m;
            prefix = p;
            suffix = s;
            this.sum = sum;
        }
    }

    static void build(int root, int start, int end) {
        tr[root] = new Node(start, end, nums[start], nums[start], nums[start], nums[start]);
        if (start == end) return;
        int mid = start + end >> 1;
        build(root << 1, start, mid);
        build(root << 1 | 1, mid + 1, end);
        pushup(root);
    }

    static void modify(int root, int index, int val) {
        if (tr[root].left == index && tr[root].right == index) {
            tr[root].max = tr[root].prefix = tr[root].suffix = tr[root].sum = val;
            return;
        }
        int mid = tr[root].left + tr[root].right >> 1;
        if (index <= mid) modify(root << 1, index, val);
        else modify(root << 1 | 1, index, val);
        pushup(root);
    }

    static void pushup(int root) {
        pushup(tr[root], tr[root << 1], tr[root << 1 | 1]);
    }

    static void pushup(Node root, Node l, Node r) {
        root.prefix = Math.max(l.prefix, l.sum + r.prefix);
        root.suffix = Math.max(r.suffix, r.sum + l.suffix);
        root.sum = l.sum + r.sum;
        root.max = Math.max(Math.max(l.max, r.max), l.suffix + r.prefix);
    }

    static Node query(int root, int start, int end) {
        if (tr[root].left >= start && tr[root].right <= end) return tr[root];
        int mid = tr[root].left + tr[root].right >> 1;
        if (mid >= end) return query(root << 1, start, end);
        if (mid < start) return query(root << 1 | 1, start, end);

        Node res = new Node(0, 0, 0, 0, 0, 0);
        Node left = query(root << 1, start, mid);
        Node right = query(root << 1 | 1, mid + 1, end);
        pushup(res, left, right);
        return res;
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
        new Thread(null, new SegmentTree(),"Main",1<<27).start();
    }

}
