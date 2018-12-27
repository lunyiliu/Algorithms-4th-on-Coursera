/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

public class Percolation_visualized {
    private int[] Array_root;
    private int[] Array_statu;
    private int[] Array_size;
    private int n;

    public Percolation_visualized(int n_) {
        Array_root = new int[n_ * n_ + 2];
        Array_statu = new int[n_ * n_ + 2];
        Array_size = new int[n_ * n_ + 2];
        for (int i = 0; i < n_ * n_ + 2; i++) {
            if (i < n_)
                Array_root[i] = n_ * n_;
                //前n个和后n个点初始根均是虚拟节点
            else if (i > n_ * n_ - n_ - 1 && i < n_ * n_)
                Array_root[i] = n_ * n_ + 1;
            else
                Array_root[i] = i;
            Array_statu[i] = 0;
            Array_size[i] = 1;
        }
        n = n_;
    }

    public static void main(String[] args) {
        double rand_row, rand_col;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        int n = 10;
        Percolation_visualized p = new Percolation_visualized(n);
        while (true) {
            try {

                for (double row = 1; row <= n; row++) {
                    for (double col = 1; col <= n; col++) {
                        //Thread.currentThread().sleep(100);
                        /*
                        if (p.Array_statu[p.root((int) row, (int) col)] == 2)
                            p.Array_statu[p.get_address((int) row, (int) col)] = 2;
                        */
                        if (p.Array_statu[p.get_address((int) row, (int) col)] == 0) {
                            StdDraw.setPenColor(StdDraw.BLACK);
                            StdDraw.filledSquare(0.5 / n + (row - 1) / n, 0.5 / n + (col - 1) / n,
                                                 0.5 / n);
                        }
                        else if (p.isfull((int) row, (int) col)) {
                            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                            StdDraw.filledSquare(0.5 / n + (row - 1) / n, 0.5 / n + (col - 1) / n,
                                                 0.5 / n);
                        }
                        else {
                            StdDraw.setPenColor(StdDraw.WHITE);
                            StdDraw.filledSquare(0.5 / n + (row - 1) / n, 0.5 / n + (col - 1) / n,
                                                 0.5 / n);
                        }
                        StdDraw.setPenColor(StdDraw.RED);
                        StdDraw.text(0.5 / n + (row - 1) / n, 0.5 / n + (col - 1) / n,
                                     String.valueOf(
                                             p.Array_root[p.get_address((int) row, (int) col)]));
                    }

                }
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(0.25 / n, 1 - 0.25 / n, String.valueOf(p.Array_root[n * n]));
                StdDraw.text(1 - 0.25 / n, 0.25 / n, String.valueOf(p.Array_root[n * n + 1]));
                if (p.percolated()) {
                    System.out.print("渗透完成，root是");
                    System.out.print(p.Array_root[n * n]);
                    System.out.print("  ");
                    System.out.print(p.Array_root[n * n + 1]);
                    break;
                }
                while (true) {
                    rand_row = Math.ceil(StdRandom.uniform() * n);
                    rand_col = Math.ceil(StdRandom.uniform() * n);
                /*
                System.out.print(rand_row);
                System.out.print(" ");
                System.out.print(rand_col);
                */
                    if (p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] == 0)
                        break;
                }
                p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] = 1;
                //分4种情况查看旁边的点是否open,是的话就union

                if (rand_row != 1 && (p.isopen((int) rand_row - 1, (int) rand_col) || p
                        .isfull((int) rand_row - 1, (int) rand_col))) {
                    p.union((int) rand_row, (int) rand_col, (int) rand_row - 1, (int) rand_col);
                    /*
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] = 2;
                    p.Array_statu[p.get_address((int) rand_row - 1, (int) rand_col)] = 2;
                    */
                }
                if (rand_row != n && (p.isopen((int) rand_row + 1, (int) rand_col) || p
                        .isfull((int) rand_row + 1, (int) rand_col))) {
                    p.union((int) rand_row, (int) rand_col, (int) rand_row + 1, (int) rand_col);
                    /*
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] = 2;
                    p.Array_statu[p.get_address((int) rand_row + 1, (int) rand_col)] = 2;
                    */
                }
                if (rand_col != 1 && (p.isopen((int) rand_row, (int) rand_col - 1) || p
                        .isfull((int) rand_row, (int) rand_col - 1))) {
                    p.union((int) rand_row, (int) rand_col, (int) rand_row, (int) rand_col - 1);
                    /*
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] = 2;
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col - 1)] = 2;
                    */
                }
                if (rand_col != n && (p.isopen((int) rand_row, (int) rand_col + 1) || p
                        .isfull((int) rand_row, (int) rand_col + 1))) {
                    p.union((int) rand_row, (int) rand_col, (int) rand_row, (int) rand_col + 1);
                    /*
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col)] = 2;
                    p.Array_statu[p.get_address((int) rand_row, (int) rand_col + 1)] = 2;
                    */
                }

                Thread.currentThread().sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
                //System.out.print(rand_row);

                break;
            }
        }
    }

    public void open(int row, int col) throws Exception {

        Array_statu[get_address(row, col)] = 1;
    }

    public int root(int row, int col) throws Exception {
        int i = get_address(row, col);
        /*
        if (i == n * n || i == n * n + 1)
            return i;
            */
        while (i != Array_root[i]) {
            if (i < n) {
                Array_root[i] = Array_root[n * n];
                return Array_root[n * n];
            }
            if ((i > n * n - n - 1) && (i < n * n)) {
                Array_root[i] = Array_root[n * n + 1];
                return Array_root[n * n + 1];
            }
            Array_root[i] = Array_root[Array_root[i]];
            i = Array_root[i];
        }
        return i;
    }

    public boolean isopen(int row, int col) throws Exception {
        return Array_statu[get_address(row, col)] == 1;
    }

    public void union(int row1, int col1, int row2, int col2) throws Exception {
        int address1 = get_address(row1, col1);
        int address2 = get_address(row2, col2);
        if (!(Math.abs(address1 - address2) == 1 || Math.abs(address1 - address2) == n))
            throw new Exception("合并的两个元素并不相邻");
        else {
            int root1 = root(row1, col1);
            int root2 = root(row2, col2);
            int size1 = Array_size[root1];
            int size2 = Array_size[root2];
            if (size1 < size2) {
                Array_root[root1] = root2;
                Array_size[root2] = size1 + size2;
                Array_statu[root1] = 2;
                Array_statu[root2] = 2;
            }
            else {
                Array_root[root2] = root1;
                Array_size[root1] = size1 + size2;
                Array_statu[root1] = 2;
                Array_statu[root2] = 2;
            }
            if ((get_address(row1, col1) > n * n - n - 1 && get_address(row1, col1) < n * n) || (
                    get_address(row1, col1) < n))
                Array_statu[get_address(row1, col1)] = 2;
            if ((get_address(row2, col2) > n * n - n - 1 && get_address(row2, col2) < n * n) || (
                    get_address(row2, col2) < n))
                Array_statu[get_address(row2, col2)] = 2;
        }
    }

    public boolean isfull(int row, int col) throws Exception {
        return Array_statu[get_address(row, col)] == 2;
    }

    public boolean percolated() throws Exception {
        return root(0, 0) == root(n, n + 1);
    }


    public final int get_address(int row, int col) throws Exception {
        int address;
        if (row == 0 && col == 0)
            address = n * n;
        else if (row == n && col == n + 1)
            address = n * n + 1;
        else
            address = (row - 1) * n + col - 1;
        if (address >= 0 || address <= n * n + 1)
            return address;
        else
            throw new Exception("地址超限");
    }
}

