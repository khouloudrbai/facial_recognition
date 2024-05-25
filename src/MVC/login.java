package MVC;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class login extends JPanel {
    JTextField name = new JTextField(10);
    JTextField pass = new JPasswordField(10);
    int x = 200, y = 200;

    // Load the original image
    ImageIcon originalIcon = new ImageIcon("Images/Connect.png");

    // Resize the image to a smaller size
    Image resizedImage = originalIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    ImageIcon resizedIcon = new ImageIcon(resizedImage);

    JButton bconn = new JButton(resizedIcon);
    
 
    HashMap<String, String> m = new HashMap<>();
    public login() {
        setLayout(new GridBagLayout());

        name.setText("bolici");
        pass.setText("1234");

        

        // Add welcome text
        JLabel welcomeLabel = new JLabel("Welcome to the Login ");
        GridBagConstraints welcomeConstraints = new GridBagConstraints();
        welcomeConstraints.gridx = 0;
        welcomeConstraints.gridy = 0;
        welcomeConstraints.gridwidth = 2; // Span across 2 columns
        welcomeConstraints.insets = new Insets(-100, 0, 20, 0); // Add some spacing
        add(welcomeLabel, welcomeConstraints);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        add(name, gbc);

        gbc.gridy = 1;
        add(pass, gbc);

        gbc.gridy = 2;
        add(bconn, gbc); // Add the new Login button
        m.put("admin", "1234");

        bconn.setBorderPainted(false);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        chargerm();

        bconn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (m.containsKey(name.getText()) && m.get(name.getText()).equals(pass.getText())) {
                        setVisible(false);
                        app.user = name.getText();
                        app.phasechoix();
                        revalidate();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong username or password");
                    }
                } catch (NullPointerException ee) {
                    JOptionPane.showMessageDialog(null, "Wrong username or password");
                }
            }
        });
    }

    void chargerm() {
        Connection conn;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
            System.out.println("Connexion réussie");
            java.sql.Statement st = conn.createStatement();
            String query = "Select pseudo,code from policier;";
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                m.put(rs.getString(1), rs.getString(2));
            }

            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}