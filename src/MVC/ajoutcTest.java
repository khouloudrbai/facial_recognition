package MVC;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AjoutcTest {

    @BeforeAll
    static void setUp() {
        // Set up any necessary resources or configurations
    }

    @Test
    void testFieldsAreClearedOnCancel() {
        ajoutc ajoutcInstance = new ajoutc();

        // Set some values in the fields
        ajoutcInstance.nom.setText("John");
        ajoutcInstance.prenom.setText("Doe");
        ajoutcInstance.cin.setText("123456");
        ajoutcInstance.daten.setText("01/01/1990");
        ajoutcInstance.occupation.setText("Engineer");
        ajoutcInstance.comboBox.setSelectedIndex(1);
        ajoutcInstance.path.setText("/path/to/image.jpg");

        // Trigger cancel action
        ajoutcInstance.ba.doClick();

        // Check if fields are cleared
        assertEquals("", ajoutcInstance.nom.getText(), "Name field not cleared");
        assertEquals("", ajoutcInstance.prenom.getText(), "Surname field not cleared");
        assertEquals("", ajoutcInstance.cin.getText(), "CIN field not cleared");
        assertEquals("", ajoutcInstance.daten.getText(), "Date field not cleared");
        assertEquals("", ajoutcInstance.occupation.getText(), "Occupation field not cleared");
        assertEquals(0, ajoutcInstance.comboBox.getSelectedIndex(), "ComboBox not reset");
        assertEquals("", ajoutcInstance.path.getText(), "Path field not cleared");
    }

    @Test
    void testPersonAddedSuccessfullyOnValidate() {
        // Mock the necessary components or dependencies (database, user permissions, etc.)
        // ...

        ajoutc ajoutcInstance = new ajoutc();

        // Set values in the fields
        ajoutcInstance.nom.setText("John");
        ajoutcInstance.prenom.setText("Doe");
        ajoutcInstance.cin.setText("123456");
        ajoutcInstance.daten.setText("01/01/1990");
        ajoutcInstance.occupation.setText("Engineer");
        ajoutcInstance.comboBox.setSelectedIndex(1);
        ajoutcInstance.path.setText("/path/to/image.jpg");

        // Trigger validate action
        ajoutcInstance.bv.doClick();

        // Check if a person with the specified CIN is added successfully to the database
        // Assert the state of the application or check the database
        // (Note: This requires appropriate mocking or setup for database interaction)
    }

    // Add more test methods based on the functionalities of your class

    // Optionally, add cleanup methods using @AfterEach, @AfterAll, etc.
}
