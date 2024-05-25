package MVC;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

class LoginTest {

    @Test
    void testLoginWithCorrectCredentials() {
        login loginInstance = new login();
        JTextField nameField = loginInstance.name;
        JPasswordField passField = (JPasswordField) loginInstance.pass;

        // Set correct credentials
        nameField.setText("bolici");
        passField.setText("1234");

        // Simulate button click
        loginInstance.bconn.doClick();

        // Check if the user is logged in
        assertFalse(loginInstance.isVisible(), "Login panel should be invisible after successful login");
        assertEquals("bolici", app.user, "User should be set to 'admin' after successful login");
    }

    @Test
    void testLoginWithIncorrectCredentials() {
        login loginInstance = new login();
        JTextField nameField = loginInstance.name;
        JPasswordField passField = (JPasswordField) loginInstance.pass;

        // Set incorrect credentials
        nameField.setText("nonexistentuser");
        passField.setText("wrongpassword");

        // Simulate button click
        loginInstance.bconn.doClick();

        // Check if an error message is displayed
        assertTrue(loginInstance.isVisible(), "Login panel should remain visible after unsuccessful login");
        assertNull(app.user, "User should be null after unsuccessful login");
        // (You might want to check for a specific error message as well)
    }

    // Add more test methods based on the functionalities of your class

    // Optionally, add cleanup methods using @AfterEach, @AfterAll, etc.
}
