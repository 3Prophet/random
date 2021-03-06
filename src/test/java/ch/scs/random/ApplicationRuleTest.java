package ch.scs.random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import ch.scs.random.utils.FileChoice;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

class ApplicationRuleTest extends ApplicationTest {

    private Application application = null;

    @Mock
    private FileChoice chooser;

    @BeforeAll
    public static void setupSpec() throws TimeoutException, InterruptedException {

        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        FxToolkit.registerPrimaryStage();
    }

    @BeforeEach
    public void setUp() throws TimeoutException, InterruptedException {
        FxToolkit.showStage();

        application = FxToolkit.setupApplication(ClickApplication.class);

        MockitoAnnotations.initMocks(this);

        when(chooser.showOpenDialog(any()))
                .thenReturn(new File("D:\\stw\\workspaces\\new\\random\\src\\main\\resources\\Ausgabeschalter_angezogen_rechts.bmp"));
        ((ClickApplication) application).setFileChooser(chooser);

    }

    // @Test
    void should_contain_button() {
        // expect:
        verifyThat("#button", hasText("click me!"));
    }

    // @Test
    void should_click_on_button() {
        // when:
        clickOn("#button");

        // then:
        verifyThat("#button", hasText("clicked!"));
    }

    @ParameterizedTest(name = "[{index}] test with filename: {0}, comparison result: {1}")
    @MethodSource("imageComparisonSourceData")
    void displayed_image_is_equal_to_reference_image_used_for_its_construction(String fileName, long result) {
        ImageView imageView = (ImageView) find("#imageView");
        Image guiImage = imageView.getImage();
        Image refImage = new Image(getClass().getClassLoader()
                .getResourceAsStream(fileName));
        assertThat(computeDifference(guiImage, refImage), is(result));
    }

    // @Test
    void displayed_image_is_not_equal_to_reference_image_not_used_for_its_construction() {
        ImageView imageView = (ImageView) find("#imageView");
        Image guiImage = imageView.getImage();
        Image refImage = new Image(getClass().getClassLoader()
                .getResourceAsStream("Aufloesevorbereiter_abgefallen_Kontakt_leitend_links.bmp"));
        assertThat(computeDifference(guiImage, refImage), is(-1L));
    }

    // @Test
    void displayed_image_is_not_equal_to_reference_image_when_one_bit_of_latter_flipped() throws IOException {
        ImageView imageView = (ImageView) find("#imageView");

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("Ausgabeschalter_angezogen_rechts.bmp");
        byte[] array = is.readAllBytes();

        int length = array.length;
        System.out.println("Array length: " + length);
        Random randomNum = new Random();

        // Reference Image Ausgabeschalter_angezogen_rechts.bmp has dimensions 88 x 48
        // (4224 pixels)
        // Byte array produced from the same image has length of 5302 hence first 1078
        // (5302-4224) elements
        // are header. Random bit flips produced in header part are not always (!)
        // detected
        // by function computeSnapshotSimilarity(...)
        int randomIndex = 1078 + randomNum.nextInt(4224);
        int randomBitShift = randomNum.nextInt(8);
        // random bit toggle in a random byte
        // System.out.println("Random bit:" + randomBitShift);
        // System.out.println("Random index: " + randomIndex);
        // System.out.println("" + array[randomIndex]);
        array[randomIndex] = (byte) (array[randomIndex] ^ (1 << randomBitShift));
        // System.out.println("" + array[randomIndex]);
        is = new ByteArrayInputStream(array);

        Image guiImage = imageView.getImage();
        Image refImage = new Image(is);
        assertThat(computeDifference(guiImage, refImage), is(1L));
    }

    public static Iterable<Object[]> imageComparisonSourceData() {
        return Arrays.asList(new Object[][] { { "Ausgabeschalter_angezogen_rechts.bmp", -1L },
                { "Aufloesevorbereiter_abgefallen_Kontakt_leitend_links.bmp", 0L } });
    }

    @Test
    public void open_file_test() {
        sleep(2000);
        clickOn("#fileMenu").clickOn("#openFile");

        WaitForAsyncUtils.waitForFxEvents();

        sleep(2000);
    }

    public void byte_array_from_reference_bmp_is_equal_to_that_from_image_from_same_bmp() throws IOException {
        ImageView imageView = (ImageView) find("#imageView");
        BufferedImage bImage;
        bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        ImageIO.write(bImage, "bmp", s);
        byte[] guiArray = s.toByteArray();
        s.close();

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("Ausgabeschalter_angezogen_rechts.bmp");
        byte[] refArray = is.readAllBytes();
        System.out.println(refArray.length);
        System.out.println(guiArray.length);

        assertThat(Arrays.equals(guiArray, refArray), is(true));
    }

    @AfterEach
    public void cleanAfterTest() throws TimeoutException {
        FxToolkit.cleanupApplication(application);
        FxToolkit.hideStage();

    }

    @AfterAll
    public static void cleanAfterAllTests() {
        Platform.exit();
    }

    /**
     * Retrieves element from the labeled GUI
     * 
     * @param query
     *            Name of the element to retrieve.
     * @return
     */
    public <T extends Node> T find(final String query) {
        return lookup(query).query();
    }

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