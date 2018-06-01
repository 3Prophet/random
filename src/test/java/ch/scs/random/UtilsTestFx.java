package ch.scs.random;

import java.util.stream.IntStream;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class UtilsTestFx {

    /**
     * Compute the difference of two JavaFX images.
     * 
     * @param image1
     *            The first image to test.
     * @param image2
     *            The second image to test.
     * @return O if images are equal, -1 if their dimensions do not match and positive number reflecting the number of different pixels otherwise.
     * 
     * @throws NullPointerException
     *             If image1 or image2 is null.
     */
    public static long computeDifference(final Image image1, final Image image2) {
        final int width1 = (int) image1.getWidth();
        final int height1 = (int) image1.getHeight();

        final int width2 = (int) image2.getWidth();
        final int height2 = (int) image2.getHeight();

        if (width1 != width2 || height1 != height2) {
            return -1L;
        }

        final PixelReader reader1 = image1.getPixelReader();
        final PixelReader reader2 = image2.getPixelReader();

        return IntStream.range(0, width1)
                .parallel()
                .mapToLong(i -> IntStream.range(0, height1)
                        .parallel()
                        .filter(j -> reader1.getArgb(i, j) != reader2.getArgb(i, j))
                        .count())
                .sum();
    }
}
