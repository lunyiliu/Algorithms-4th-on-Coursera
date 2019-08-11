/******************************************************************************
 *  Compilation:  javac ResizeDemo.java
 *  Execution:    java ResizeDemo input.png columnsToRemove rowsToRemove
 *  Dependencies: SeamCarver.java SCUtility.java
 *
 *
 *  Read image from file specified as command line argument. Use SeamCarver
 *  to remove number of rows and columns specified as command line arguments.
 *  Show the images and print time elapsed to screen.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class ResizeDemo {
    public static void main(String[] args) {
        if (args.length != 3) {
            StdOut.println(
                    "Usage:\njava ResizeDemo [image filename] [num cols to remove] [num rows to remove]");
            return;
        }

        Picture inputImg = new Picture(args[0]);
        int removeColumns = Integer.parseInt(args[1]);
        int removeRows = Integer.parseInt(args[2]);

        StdOut.printf("image is %d columns by %d rows\n", inputImg.width(), inputImg.height());
        SeamCarver sc = new SeamCarver(inputImg);

        Stopwatch sw = new Stopwatch();
/*
        for (int i = 0; i < removeRows; i++) {
            int[] horizontalSeam = { 0, 1, 2, 3, 4, 4 };
            sc.removeHorizontalSeam(horizontalSeam);
        }
        */

        for (int i = 0; i < removeColumns; i++) {
            //int[] verticalSeam = sc.findVerticalSeam();
            int[] verticalSeam = { 0, 1, 2, 1, 1, 0 };
            sc.removeVerticalSeam(verticalSeam);
        }

        Picture outputImg = sc.picture();

        StdOut.printf("new image size is %d columns by %d rows\n", sc.width(), sc.height());

        StdOut.println("Resizing time: " + sw.elapsedTime() + " seconds.");
        StdOut.printf("%s (%d-by-%d image)\n", inputImg, inputImg.width(), inputImg.height());
        StdOut.printf("%s (%d-by-%d image)\n", outputImg, outputImg.width(), outputImg.height());
        //inputImg.show();
        //outputImg.show();
    }

}
