package MVC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

import static org.junit.jupiter.api.Assertions.*;

class chercherTest {

    private cherche chercheInstance;

    @BeforeEach
    void setUp() {
        // Initialize the Cherche instance before each test
    	cherche chercheInstance = new cherche();
    }

    @AfterEach
    void tearDown() {
        // Clean up resources after each test
        chercheInstance = null;
    }

 

    @Test
    void testBaAction() {
        // Simulate setting a file path in the text field
        cherche.t1.setText("Images/khouloud.jpg");

        // Simulate the action of the "ba" button
        cherche.ba.doClick();

        // TODO: Add assertions to verify the expected behavior based on the simulated action

        // Example assertion: Check if the JTextArea t2 has been updated with user information
        assertNotEquals("Info", cherche.t2.getText());
    }
    // TODO: Add more test methods for other actions and scenarios
}
