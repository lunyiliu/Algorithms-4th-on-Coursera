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

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class test {


    public static void main(String[] args) {
        int[][] pixel = {
                { 0x000409, 0x020509, 0x010000, 0x030403, 0x080904, 0x050905 },
                { 0x050906, 0x070708, 0x020800, 0x000203, 0x040208, 0x080808 },
                { 0x080806, 0x020004, 0x050805, 0x060808, 0x050001, 0x010607 },
                { 0x010108, 0x040506, 0x010809, 0x040705, 0x040504, 0x010000 },
                { 0x090406, 0x080203, 0x080901, 0x010902, 0x000003, 0x040407 },
                { 0x050702, 0x000501, 0x090605, 0x050006, 0x000309, 0x060709 }
        };
        Picture p = new Picture(6, 6);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                p.setRGB(i, j, pixel[j][i]);
            }

        }
        //StdOut.printf("%s (%d-by-%d image)\n", p, p.width(), p.height());
        SeamCarver sc = new SeamCarver(p);
        int[] seam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(seam);
        StdOut.printf("%s (%d-by-%d image)\n", sc.picture(), sc.width(), sc.height());
        Picture graph = sc.picture();
        Color[][] pixels = new Color[graph.width()][graph.height()];
        for (int x = 0; x < graph.width(); x++) {
            for (int y = 0; y < graph.height(); y++) {
                pixels[x][y] = graph.get(x, y);
            }
        }
        Color Right = pixels[2][2];
        //StdOut.println(Right.getBlue());
        Color Left = pixels[0][2];
        Color Up = pixels[1][1];
        Color Down = pixels[1][3];
        double energy = Math
                .sqrt(Math.pow(Right.getRed() - Left.getRed(), 2) + Math
                        .pow(Right.getBlue() - Left.getBlue(), 2) + Math
                        .pow(Right.getGreen() - Left.getGreen(), 2) + Math.pow(
                        Up.getRed()
                                - Down.getRed(), 2) + Math
                        .pow(Up.getBlue() - Down.getBlue(), 2)
                              + Math.pow(Up.getGreen() - Down.getGreen(), 2));
        StdOut.println(sc.energy(1, 2));
        StdOut.println(energy);
    }
}
