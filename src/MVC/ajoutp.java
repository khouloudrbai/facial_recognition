package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class ajoutp extends JPanel {
    JPanel panel_2 = new JPanel();
    JTextField lblNom = new JTextField("Nom");
    JTextField lblPrenom = new JTextField("Prenom");
    JTextField lblCode = new JTextField("code");
    JTextField nom = new JTextField("");
    JTextField prenom = new JTextField("");
    JTextField pseudo = new JTextField("");
    JPasswordField code = new JPasswordField("");
    JComboBox comboBox = new JComboBox();
    JButton ba = new JButton("annuler");
    JButton bv = new JButton("valider");

    public ajoutp() {
        setSize(403, 485);
        setLocation(50, 50);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.println("Helin");
        }
        panel_2.setLayout(null);
        panel_2.setBounds(24, 55, 371, 288);

        lblNom.setBounds(24, 32, 46, 14);
        panel_2.add(lblNom);
        lblNom.setVisible(true);
        revalidate();
        repaint();

        nom.setBounds(73, 29, 86, 20);
        panel_2.add(nom);
        nom.setColumns(10);

        prenom.setBounds(73, 81, 86, 20);
        panel_2.add(prenom);
        prenom.setColumns(10);

        lblPrenom.setBounds(24, 84, 46, 14);
        panel_2.add(lblPrenom);

        lblCode.setBounds(24, 187, 46, 14);
        panel_2.add(lblCode);

        JTextField lblGrade = new JTextField("Grade");
        lblGrade.setBounds(24, 240, 46, 14);
        panel_2.add(lblGrade);

        code.setBounds(73, 184, 86, 20);
        panel_2.add(code);

        comboBox.setModel(new DefaultComboBoxModel(new String[]{"A1", "A2", "A3"}));
        comboBox.setBounds(73, 237, 28, 20);
        panel_2.add(comboBox);

        JTextField label = new JTextField("pseudo");
        label.setBounds(24, 132, 46, 14);
        panel_2.add(label);

        pseudo.setColumns(10);
        pseudo.setBounds(73, 129, 86, 20);
        panel_2.add(pseudo);

        setLayout(null);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(34, 354, 369, 34);

        panel_1.add(ba);

        panel_1.add(bv);
        add(panel_2);
        add(panel_1);
        JTextField lblNewLabel = new JTextField("Ajouter un policeman:");
        lblNewLabel.setBounds(172, 11, 158, 33);
        add(lblNewLabel);
        setVisible(false);
        revalidate();
        repaint();

        ba.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                pseudo.setText("");
                nom.setText("");
                prenom.setText("");
                code.setText("");
            }
        });

        bv.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;

                try {
                    // Load the JDBC driver and establish a connection
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
                    System.out.println("Connexion réussie");

                    java.sql.Statement st = conn.createStatement();
                    String query = "Select grade from policier where pseudo='" + app.user + "' ;";
                    ResultSet rs = st.executeQuery(query);

                    // Organization des données
                    String s = "";
                    if (rs.next()) {
                        s = rs.getString(1);
                    }
                    rs.close();
                    conn.close();

                    if (!s.equals("A3")) {
                        JOptionPane.showMessageDialog(null, "Vous n'avez pas le droit");
                    } else {
                        String enteredPseudo = pseudo.getText().trim().toLowerCase();

                        // Check if the entered pseudo already exists (case-insensitive)
                        query = "SELECT COUNT(*) FROM policier WHERE LOWER(pseudo) = '" + enteredPseudo + "'";
                        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
                        st = conn.createStatement();
                        rs = st.executeQuery(query);

                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "Pseudo déjà existe!");
                        } else {
                            // Insert the new policeman
                            query = "INSERT INTO `policier`(`nom`, `prenom`, `pseudo`, `code`, `grade`) VALUES('"
                                    + (nom.getText()) + "','" + (prenom.getText()) + "','" + enteredPseudo + "','"
                                    + (code.getText()) + "','" + (comboBox.getSelectedItem()) + "')";

                            st.executeUpdate(query);
                            JOptionPane.showMessageDialog(null, "L'ajout est confirmé");
                            //conn.commit();
                        }

                        rs.close();
                        conn.close();
                    }

                } catch (Exception ee) {
                    // To see problems
                    System.out.println("Hi");
                    ee.printStackTrace();
                }
            }

        });
    }
}
