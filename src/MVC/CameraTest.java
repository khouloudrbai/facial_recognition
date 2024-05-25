package MVC;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CameraTest {

    private camera testCamera;

    @BeforeEach
    void setUp() {
        testCamera = new camera();
        testCamera.start();
    }

    @AfterEach
    void tearDown() {
        testCamera.destroy();
    }

    @Test
    void testTakePhoto() {
        // Action
        BufferedImage image = testCamera.captureImage();

        // Assertion
        assertNotNull(image);

        // Additional checks on the image, if needed

        // Save the image to a file (optional)
        try {
            File imageFile = new File("testImage.jpg");
            ImageIO.write(image, "JPG", imageFile);
            assertTrue(imageFile.exists());
        } catch (IOException e) {
            fail("Failed to write the image to a file");
        }
    }
}
