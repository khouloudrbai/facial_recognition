package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.github.sarxos.webcam.WebcamResolution;

public class ajoutc extends JPanel {
    private static HashMap<String, Mat> processedImagesHashMap = new HashMap<>();

    JFrame f = new JFrame();
    JPanel panel_2 = new JPanel();
    JButton bs = new JButton(Fonctionalite.ResizeImage("Images/downoald.png", 20, 15));
    JButton bc = new JButton(Fonctionalite.ResizeImage("Images/camera.png", 70, 15));
    private JTextField textField_1;
    JTextField nom;
     JTextField prenom;
     JTextField cin;
     JTextField daten;
     JTextField occupation;
     JTextField path;
     JTextField lnom = new JTextField();
     JTextField lprenom = new JTextField();
     JTextField lcin = new JTextField();
     JTextField ldaten = new JTextField();
     JTextField loccupation = new JTextField();
     JTextField lpath = new JTextField();
    JComboBox comboBox = new JComboBox();
    JButton ba = new JButton("annuler");
    JButton bv = new JButton("valider");

    public ajoutc() {
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);
        panel_2.setBounds(22, 55, 371, 325);
        setSize(403, 459);
        setLocation(35, 47);

        add(panel_2);

        nom = new JTextField();
        lnom.setText("nom");
        nom.setBounds(50, 24, 86, 20);
        lnom.setBounds(0, 24, 46, 20);
        panel_2.add(lnom);
        panel_2.add(nom);
        nom.setColumns(10);

        prenom = new JTextField();
        lprenom.setText("prenom");
        prenom.setBounds(50, 69, 86, 20);
        lprenom.setBounds(0, 69, 46, 20);
        panel_2.add(lprenom);
        panel_2.add(prenom);
        prenom.setColumns(10);

        cin = new JTextField();
        lcin.setText("C.I.N");
        cin.setBounds(50, 113, 86, 20);
        lcin.setBounds(0, 113, 46, 20);
        panel_2.add(cin);
        panel_2.add(lcin);
        cin.setColumns(10);

        daten = new JTextField();
        ldaten.setText("daten");
        daten.setBounds(50, 158, 86, 20);
        panel_2.add(daten);
        ldaten.setBounds(0, 158, 46, 20);
        panel_2.add(ldaten);
        daten.setColumns(10);

        occupation = new JTextField();
        loccupation.setText("occupation");
        occupation.setBounds(50, 202, 86, 20);
        panel_2.add(occupation);
        loccupation.setBounds(0, 202, 46, 20);
        panel_2.add(loccupation);
        occupation.setColumns(10);

        path = new JTextField();
        lpath.setText("path");
        path.setBounds(220, 24, 106, 20);
        panel_2.add(path);
        lpath.setBounds(171, 24, 46, 20);
        panel_2.add(lpath);
        path.setColumns(20);

        comboBox.setModel(new DefaultComboBoxModel(new String[]{"NOM", "OUI"}));
        comboBox.setBounds(240, 69, 48, 20);
        panel_2.add(comboBox);
        textField_1 = new JTextField("Recherché?");
        textField_1.setBounds(171, 69, 62, 20);
        panel_2.add(textField_1);

        setLayout(null);
        JTextField lblNewLabel = new JTextField("Ajouter un citoyen:");
        lblNewLabel.setBounds(172, 11, 158, 33);
        add(lblNewLabel);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(24, 402, 369, 34);
        add(panel_1);

        panel_1.add(ba);

        panel_1.add(bv);
        repaint();
        validate();

        setVisible(false);
        ba.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cin.setText("");
                nom.setText("");
                prenom.setText("");
                daten.setText("");
                comboBox.setSelectedIndex(0);
                path.setText("");
                occupation.setText("");
                f.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        f.dispose();
                        cherche.c.stop();
                        String imagePath = cherche.c.a;

                        // Perform face detection and image processing
                        detectAndProcessFace(imagePath);

                        // Set the path in the text field
                        path.setText(imagePath);
                    }
                });
            }
        });
        bv.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Connection conn;

                try {
                    // Connect to the database
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
                    System.out.println("Connection successful");
                    java.sql.Statement st = conn.createStatement();

                    // Check user permissions
                    String query = "SELECT grade FROM policier WHERE pseudo='" + app.user + "'";
                    ResultSet rs = st.executeQuery(query);

                    // Check if the user has the required permissions
                    String userGrade = "";
                    if (rs.next()) {
                        userGrade = rs.getString(1);
                    }
                    rs.close();

                    if (!userGrade.equals("A3")) {
                        JOptionPane.showMessageDialog(null, "You don't have the required permissions.");
                    } else {
                        // Check if the given CIN already exists
                        query = "SELECT COUNT(*) FROM personne WHERE cin='" + cin.getText() + "'";
                        rs = st.executeQuery(query);

                        if (rs.next() && rs.getInt(1) > 0) {
                            JOptionPane.showMessageDialog(null, "CIN already exists!");
                        } else {
                            // Insert the person into the database
                            query = "INSERT INTO personne(nom, prenom, cin, datenai, occupation, recherchee, pathorigin) VALUES('"
                                    + nom.getText() + "','" + prenom.getText() + "','" + cin.getText() + "','"
                                    + daten.getText() + "','" + occupation.getText() + "','" + comboBox.getSelectedItem()
                                    + "','" + path.getText() + "')";

                            try {
                                st.executeUpdate(query);
                                JOptionPane.showMessageDialog(null, "Person added successfully");

                                // Load the saved image
                                Mat processedImage = Imgcodecs.imread(path.getText());

                                // Resize the image (optional, adjust the size as needed)
                                Size newSize = new Size(120, 120);
                                Imgproc.resize(processedImage, processedImage, newSize);

                                // Save the processed image matrix in the HashMap
                                processedImagesHashMap.put(cin.getText(), processedImage);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "CIN already exists!");
                            } finally {
                                try {
                                    conn.close();
                                } catch (SQLException ee) {
                                    JOptionPane.showMessageDialog(null, "SQL Error!");
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    
    
       
        bc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                cherche.c.init();
                cherche.c.start();
                f.pack();
                f.add(cherche.c.panel);
                f.setLocation(450, 300);
                f.setSize(WebcamResolution.QVGA.getSize());
                f.setVisible(true);
                f.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        f.dispose();
                        cherche.c.stop();
                        path.setText(cherche.c.a);
                    }
                });

            }
        });

        bc.setBounds(280, 180, 70, 23);
        add(bc);
        bs.setBounds(195, 180, 70, 23);
        add(bs);
        bs.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File("Images"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = file.getSelectedFile();
                    String p = selectedFile.getAbsolutePath();
                    path.setText(p);

                    // Note: You may want to handle the image loading logic separately
                    // based on your requirements.
                    try {
                        // Assuming you have a class called JPanelWithBackground
                        // that sets a background image for the JPanel.
                        // You need to replace it with your actual logic to set the image.
                        add(new JPanelWithBackground(p));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (Exception ek) {
                        ek.printStackTrace();
                    }

                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Select");
                }
                repaint();
                validate();
            }
        });
    }
    private void detectAndProcessFace(String imagePath) {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        // Load the image
        Mat image = Imgcodecs.imread(imagePath);

        // Load the pre-trained face detector (you may need to change the path)
        String cascadePath = "Images/haarcascade_frontalface_default.xml";
        org.opencv.objdetect.CascadeClassifier faceCascade = new org.opencv.objdetect.CascadeClassifier();
        faceCascade.load(cascadePath);

        // Detect faces in the image
        MatOfRect faceDetections = new MatOfRect();
        faceCascade.detectMultiScale(image, faceDetections);

        // Draw a green rectangle around each detected face
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, rect.tl(), rect.br(), new Scalar(0, 255, 0), 3);
        }

        // Resize the image (optional, adjust the size as needed)
        Size newSize = new Size(120, 120);
        Imgproc.resize(image, image, newSize);

        // Convert the image to a matrix for further processing
        // You can store this matrix or use it directly as needed
        int rows = image.rows();
        int cols = image.cols();
        int type = image.type();
        byte[] data = new byte[rows * cols * (int) (image.elemSize())];
        image.get(0, 0, data);
        Mat result = new Mat(rows, cols, type);
        result.put(0, 0, data);

        // Save the processed image (optional, adjust the path as needed)
        String processedImagePath = "Images/image.jpg";
        Imgcodecs.imwrite(processedImagePath, result);
    }
    
    public static HashMap<String, Mat> getProcessedImagesHashMap() {
        return processedImagesHashMap;
    }
}
