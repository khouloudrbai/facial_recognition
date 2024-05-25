package MVC;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

class AjoutpTest {

    @Test
    void testAddingPolicemanWithCorrectData() {
        ajoutp ajoutpInstance = new ajoutp();
        JTextField pseudoField = ajoutpInstance.pseudo;
        JTextField nomField = ajoutpInstance.nom;
        JTextField prenomField = ajoutpInstance.prenom;
        JPasswordField codeField = ajoutpInstance.code;

        // Set correct data
        pseudoField.setText("newuser");
        nomField.setText("John");
        prenomField.setText("Doe");
        codeField.setText("1234");

        // Simulate button click
        ajoutpInstance.bv.doClick();

        // TODO: Assert the expected behavior after adding a new policeman
    }

    @Test
    void testAddingPolicemanWithExistingPseudo() {
        ajoutp ajoutpInstance = new ajoutp();
        JTextField pseudoField = ajoutpInstance.pseudo;
        JTextField nomField = ajoutpInstance.nom;
        JTextField prenomField = ajoutpInstance.prenom;
        JPasswordField codeField = ajoutpInstance.code;

        // Set data with an existing pseudo
        pseudoField.setText("existinguser");
        nomField.setText("John");
        prenomField.setText("Doe");
        codeField.setText("1234");

        // Simulate button click
        ajoutpInstance.bv.doClick();

        // TODO: Assert the expected behavior after attempting to add a new policeman with an existing pseudo
    }

    @Test
    void testAddingPolicemanWithInsufficientPermissions() {
        ajoutp ajoutpInstance = new ajoutp();
        JTextField pseudoField = ajoutpInstance.pseudo;
        JTextField nomField = ajoutpInstance.nom;
        JTextField prenomField = ajoutpInstance.prenom;
        JPasswordField codeField = ajoutpInstance.code;

        // Set correct data
        pseudoField.setText("newuser");
        nomField.setText("John");
        prenomField.setText("Doe");
        codeField.setText("1234");

        // Set the user grade to A2 (insufficient permissions)
        app.user = "someuser";

        // Simulate button click
        ajoutpInstance.bv.doClick();

        // TODO: Assert the expected behavior after attempting to add a new policeman with insufficient permissions
    }

    // Add more test methods based on the functionalities of your class

    // Optionally, add cleanup methods using @AfterEach, @AfterAll, etc.
}
