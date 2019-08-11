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

public class SeamCarver {
    private Picture graph;
    private int width;
    private int height;
    private Color[][] pixels;
    private double[][] energy;

    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new java.lang.IllegalArgumentException();
        }
        graph = new Picture(picture.width(), picture.height());
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                graph.setRGB(i, j, picture.getRGB(i, j));
            }

        }
        width = graph.width();
        height = graph.height();
        energy = new double[graph.width()][graph.height()];
        pixels = new Color[width][height];
        for (int x = 0; x < graph.width(); x++) {
            for (int y = 0; y < graph.height(); y++) {
                pixels[x][y] = graph.get(x, y);
            }
        }
        for (int x = 0; x < graph.width(); x++) {
            for (int y = 0; y < graph.height(); y++) {
                pixels[x][y] = graph.get(x, y);
                if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
                    energy[x][y] = 1000;
                }
                else {
                    Color Right = pixels[x + 1][y];
                    //StdOut.println(Right.getBlue());
                    Color Left = pixels[x - 1][y];
                    Color Up = pixels[x][y - 1];
                    Color Down = pixels[x][y + 1];
                    energy[x][y] = Math
                            .sqrt(Math.pow(Right.getRed() - Left.getRed(), 2) + Math
                                    .pow(Right.getBlue() - Left.getBlue(), 2) + Math
                                    .pow(Right.getGreen() - Left.getGreen(), 2) + Math.pow(
                                    Up.getRed()
                                            - Down.getRed(), 2) + Math
                                    .pow(Up.getBlue() - Down.getBlue(), 2)
                                          + Math.pow(Up.getGreen() - Down.getGreen(), 2));
                }
            }
        }
    }              // create a seam carver object based on the given pictur

    public Picture picture() {
        Picture out_p = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                out_p.setRGB(i, j, graph.getRGB(i, j));
            }

        }
        return out_p;
    }                    // current picture

    public int width() {
        return width;
    }                           // width of current picture

    public int height() {
        return height;
    }                           // height of current picture

    public double energy(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new java.lang.IllegalArgumentException();
        }
        return energy[x][y];
    }               // energy of pixel at column x and row y

    public int[] findHorizontalSeam() {
        double shortest = Double.POSITIVE_INFINITY;
        int shortest_pos = 0;
        int[] SP = new int[width()];
        for (int i = 0; i < height(); i++) {
            double[][] dists
                    = new double[width()][height()];// for every vetex in left column, there is a dist[][]
            int[][] edgetos = new int[width()][height()];
            for (int j = 0; j < width(); j++) {
                for (int k = 0; k < height(); k++) {
                    if (j != 0) {
                        dists[j][k] = Double.POSITIVE_INFINITY;
                    }
                    else {
                        dists[j][k] = 0;
                    }
                    edgetos[j][k] = -1;

                }
            }
            //StdOut.println("i=" + i);
            for (int j = 1; j < width(); j++) {
                // StdOut.println("   j=" + j);
                for (int k = i - j; k < i + j + 1; k++) {
                    // StdOut.println("      k=" + k);
                    if (k >= 0 && k < height()) {
                        if (k < i + j - 1 && k + 1 < height()) {
                            if (dists[j - 1][k + 1] + energy[j][k] < dists[j][k]) {
                                dists[j][k] = dists[j - 1][k + 1] + energy[j][k];
                                edgetos[j][k] = k + 1;

                            }
                        }
                        if (k > i - j + 1 && k - 1 >= 0) {
                            if (dists[j - 1][k - 1] + energy[j][k] < dists[j][k]) {
                                dists[j][k] = dists[j - 1][k - 1] + energy[j][k];
                                edgetos[j][k] = k - 1;
                            }
                        }
                        if (k != i - j || k != i + j) {
                            if (dists[j - 1][k] + energy[j][k] < dists[j][k]) {
                                dists[j][k] = dists[j - 1][k] + energy[j][k];
                                edgetos[j][k] = k;
                            }
                        }
                    }
                }
            }
            double old_shortest = shortest;
            for (int j = 0; j < height(); j++) {
                if (dists[width() - 1][j] < shortest) {
                    shortest = dists[width() - 1][j];
                    // shortest_pos[0] = i;
                    shortest_pos = j;
                }
            }
            if (old_shortest != shortest) {
                int current = shortest_pos;
        /*
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                StdOut.println(
                        "x=" + i + "y=" + j + "时，edges指向左边的" + edgetos[0][i][j][1] + ", distto为"
                                + dists[0][i][j]);
            }
        }
        */
                for (int l = width() - 1; l >= 0; l--) {
                    SP[l] = current;
                    //StdOut.println("Shortest_pos[0]=" + shortest_pos[0]);
                    // StdOut.println("   current=" + current);
                    current = edgetos[l][current];
                }
            }
        }
        return SP;

    }               // sequence of indices for horizontal seam

    public int[] findVerticalSeam() {
        double[][] trans_energy = new double[height()][width()];
        //double[][] energy_origin = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                trans_energy[j][i] = energy[i][j];
                //energy_origin[i][j] = energy[i][j];
            }
        }
        energy = trans_energy;
        int tmp = height;
        height = width;
        width = tmp;
        /*
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[0].length; j++) {
                StdOut.print(energy[i][j]);
            }
        }
        */
        int[] SP = findHorizontalSeam();
        //energy = energy_origin;
        energy = new double[height][width];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                energy[j][i] = trans_energy[i][j];
                //energy_origin[i][j] = energy[i][j];
            }
        }
        tmp = height;
        height = width;
        width = tmp;
        return SP;
    }                 // sequence of indices for vertical seam

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (seam.length != width) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= height) {
                throw new java.lang.IllegalArgumentException();
            }
            if (i > 0) {
                if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        Color[][] new_pixels = new Color[width][height - 1];
        //double[][] new_energy = new double[width][height - 1];
        //StdOut.println(height);
        //StdOut.println(graph.height());
        for (int i = 0; i < width; i++) {
            //StdOut.print(new_energy[5][4]);
            System.arraycopy(pixels[i], 0, new_pixels[i], 0, seam[i]);
            System.arraycopy(pixels[i], seam[i] + 1, new_pixels[i], seam[i],
                             height - seam[i] - 1);
            /*
            System.arraycopy(energy[i], 0, new_energy[i], 0, seam[i]);
            System.arraycopy(energy[i], seam[i] + 1, new_energy[i], seam[i],
                             height - seam[i] - 1);

            if (i == 0 || i == width - 1) {
                if (seam[i] != 0) {
                    new_energy[i][seam[i] - 1] = 1000;
                }
                else {
                    new_energy[i][seam[i]] = 1000;
                }
                continue;
            }
            if (seam[i] == 0) {
                new_energy[i][seam[i]] = 1000;
                continue;
            }
            if (seam[i] == 1) {
                new_energy[i][seam[i] - 1] = 1000;
            }
            if (seam[i] == height - 1) {
                new_energy[i][seam[i] - 1] = 1000;
                continue;
            }
            if (seam[i] == height - 2) {
                new_energy[i][seam[i]] = 1000;
            }
            Color Up_2 = null, Down_2 = null;

            if (seam[i] != 1) {
                Up_2 = pixels[i][seam[i] - 2];
            }
            Color Up_1 = pixels[i][seam[i] - 1];
            Color Right_up = pixels[i + 1][seam[i] - 1];
            Color Left_up = pixels[i - 1][seam[i]];
            Color Right_down = pixels[i + 1][seam[i] + 1];
            Color Left_down = pixels[i - 1][seam[i] + 1];
            Color Down_1 = pixels[i][seam[i] + 1];
            if (seam[i] != height - 2) {
                Down_2 = pixels[i][seam[i] + 2];
            }
            if (seam[i] != 1) {

                new_energy[i][seam[i] - 1] = Math
                        .sqrt(Math.pow(Right_up.getRed() - Left_up.getRed(), 2) + Math
                                .pow(Right_up.getBlue() - Left_up.getBlue(), 2) + Math
                                .pow(Right_up.getGreen() - Left_up.getGreen(), 2) + Math.pow(
                                Up_2.getRed()
                                        - Down_1.getRed(), 2) + Math
                                .pow(Up_2.getBlue() - Down_1.getBlue(), 2)
                                      + Math.pow(Up_2.getGreen() - Down_1.getGreen(), 2));
            }
            if (seam[i] != height - 2) {
                new_energy[i][seam[i]] = Math
                        .sqrt(Math.pow(Right_down.getRed() - Left_down.getRed(), 2) + Math
                                .pow(Right_down.getBlue() - Left_down.getBlue(), 2) + Math
                                .pow(Right_down.getGreen() - Left_down.getGreen(), 2) + Math
                                .pow(
                                        Up_1.getRed()
                                                - Down_2.getRed(), 2) + Math
                                .pow(Up_1.getBlue() - Down_2.getBlue(), 2)
                                      + Math.pow(Up_1.getGreen() - Down_2.getGreen(), 2));
            }
            */
        }
        Picture new_picture = new Picture(width, height - 1);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height - 1; j++) {
                new_picture.set(i, j, new_pixels[i][j]);
            }
        }
        graph = new_picture;
        width = graph.width();
        height = graph.height();
        pixels = new_pixels;
        for (int x = 0; x < graph.width(); x++) {
            for (int y = 0; y < graph.height(); y++) {
                if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
                    energy[x][y] = 1000;
                }
                else {
                    Color Right = pixels[x + 1][y];
                    //StdOut.println(Right.getBlue());
                    Color Left = pixels[x - 1][y];
                    Color Up = pixels[x][y - 1];
                    Color Down = pixels[x][y + 1];
                    energy[x][y] = Math
                            .sqrt(Math.pow(Right.getRed() - Left.getRed(), 2) + Math
                                    .pow(Right.getBlue() - Left.getBlue(), 2) + Math
                                    .pow(Right.getGreen() - Left.getGreen(), 2) + Math.pow(
                                    Up.getRed()
                                            - Down.getRed(), 2) + Math
                                    .pow(Up.getBlue() - Down.getBlue(), 2)
                                          + Math.pow(Up.getGreen() - Down.getGreen(), 2));
                }
            }
        }

    }  // remove horizontal seam from current picture

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (seam.length != height) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width) {
                throw new java.lang.IllegalArgumentException();
            }
            if (i > 0) {
                if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        Color[][] new_pixels = new Color[width - 1][height];
        Picture new_picture = new Picture(width - 1, height);
        //double[][] new_energy = new double[width][height - 1];
        //StdOut.println(height);
        //StdOut.println(graph.height());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //StdOut.print(new_energy[5][4]);
                if (i < seam[j]) {
                    new_pixels[i][j] = pixels[i][j];
                    new_picture.set(i, j, new_pixels[i][j]);
                }
                if (i > seam[j]) {
                    new_pixels[i - 1][j] = pixels[i][j];
                    new_picture.set(i - 1, j, new_pixels[i - 1][j]);
                }

            }
        }
        graph = new_picture;
        width = graph.width();
        height = graph.height();
        pixels = new_pixels;
        for (int x = 0; x < graph.width(); x++) {
            for (int y = 0; y < graph.height(); y++) {
                if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1) {
                    energy[x][y] = 1000;
                }
                else {
                    Color Right = pixels[x + 1][y];
                    //StdOut.println(Right.getBlue());
                    Color Left = pixels[x - 1][y];
                    Color Up = pixels[x][y - 1];
                    Color Down = pixels[x][y + 1];
                    energy[x][y] = Math
                            .sqrt(Math.pow(Right.getRed() - Left.getRed(), 2) + Math
                                    .pow(Right.getBlue() - Left.getBlue(), 2) + Math
                                    .pow(Right.getGreen() - Left.getGreen(), 2) + Math.pow(
                                    Up.getRed()
                                            - Down.getRed(), 2) + Math
                                    .pow(Up.getBlue() - Down.getBlue(), 2)
                                          + Math.pow(Up.getGreen() - Down.getGreen(), 2));
                }
            }
        }
       /*
        //double[][] trans_energy = new double[height()][width()];
        energy = new double[height()][width()];
        Color[][] trans_pixels = new Color[height()][width()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                //trans_energy[j][i] = energy[i][j];
                trans_pixels[j][i] = pixels[i][j];
            }
        }
        pixels = trans_pixels;
        //energy = trans_energy;
        int tmp = height;
        height = width;
        width = tmp;
        //StdOut.println(height);
        //StdOut.println(width);
        removeHorizontalSeam(seam);
        //StdOut.printf("%s (%d-by-%d image)\n", graph, graph.width(), graph.height());
        tmp = height;
        height = width;
        width = tmp;
        double[][] trans_energy = new double[width][height];
        trans_pixels = new Color[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                trans_energy[i][j] = energy[j][i];
                trans_pixels[i][j] = pixels[j][i];
            }
        }
        energy = trans_energy;
        pixels = trans_pixels;
        Picture trans_picture = new Picture(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                trans_picture.set(i, j, pixels[i][j]);
            }
        }
        graph = trans_picture;
        */
    }   // remove vertical seam from current picture

}
