package MVC;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;

import com.github.sarxos.webcam.WebcamResolution;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class cherche extends JPanel {

    File fc;
    static JTextField t1 = new JTextField("");
    static JTextArea t2 = new JTextArea("Info");
    JPanel panel_2 = new JPanel();
    JLabel img1 = new JLabel("");
    JLabel img2 = new JLabel("");
    static camera c = new camera();
    JFrame f = new JFrame();
    JButton bs = new JButton(Fonctionalite.ResizeImage("Images/downoald.png", 20, 15));
    JButton bc = new JButton(Fonctionalite.ResizeImage("Images/camera.png", 70, 15));
    static JButton bide = new JButton(Fonctionalite.ResizeImage("Images/policeavatar.png", 40, 45));
    JButton br = new JButton(Fonctionalite.ResizeImage("Images/jail.jpg", 30, 30));
    static JButton ba = new JButton(Fonctionalite.ResizeImage("Images/chercher.jpg", 30, 30));
    JButton bh = new JButton(Fonctionalite.ResizeImage("Images/liste.png", 30, 30));

    public cherche() {
        setSize(403, 485);
        setLocation(50, 50);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        revalidate();
        repaint();
        panel_2.setLayout(null);
        panel_2.setBounds(22, 55, 371, 325);
        add(panel_2);

        setLayout(null);

        t1.setBounds(22, 11, 200, 20);
        add(t1);
        t1.setColumns(10);

        bs.setBounds(229, 10, 70, 23);
        add(bs);
        bc.setBounds(315, 10, 70, 23);
        add(bc);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(8, 402, 400, 90);

        bh.setSize(89, 23);
        panel_1.add(bh);

        ba.setSize(89, 23);
        panel_1.add(ba);

        br.setSize(89, 23);
        panel_1.add(br);

        panel_1.revalidate();
        panel_1.repaint();
        add(panel_1);

        img1.setIcon(Fonctionalite.ResizeImage("Images/personne.png", 120, 120));
        img1.setBounds(25, 11, 132, 131);
        panel_2.add(img1);
        img1.setVisible(true);

        img2.setIcon(Fonctionalite.ResizeImage("Images/personne.png", 120, 120));
        img2.setBounds(25, 153, 138, 131);
        panel_2.add(img2);
        repaint();
        validate();

        t2.setColumns(50);
        t2.setBounds(188, 94, 173, 150);
        panel_2.add(t2);

        bide.setBounds(237, 30, 89, 50);
        bide.setBorderPainted(false);
        panel_2.add(bide);
        repaint();
        validate();
        setVisible(false);

        bc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                c.init();
                c.start();
                f.pack();
                f.add(c.panel);
                f.setLocation(450, 300);
                f.setSize(WebcamResolution.QVGA.getSize());
                f.setVisible(true);
                f.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        f.dispose();
                        c.stop();
                        t1.setText(c.a);
                    }
                });
            }
        });

        bide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                String cin = Fonctionalite.recherimg(); // Assuming Fonctionalite.recherimg() returns the CIN

                // Check if the CIN exists in the HashMap
                if (ajoutc.getProcessedImagesHashMap().containsKey(cin)) {
                    // Get the processed image matrix
                    Mat processedImage = ajoutc.getProcessedImagesHashMap().get(cin);

                    // Display the processed image in img1
                    img1.setIcon(Fonctionalite.matToImageIcon(processedImage, 120, 120));

                    repaint();
                    validate();
                } else {
                    System.out.println("CIN doesn't exist in the HashMap.");
                    // Handle this case, show an error message or take appropriate action.
                }
            }
        });

       

        bs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser file = new JFileChooser();
                file.setCurrentDirectory(new File("Images"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "gif", "png");
                file.addChoosableFileFilter(filter);
                int result = file.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                	File selectedFile = file.getSelectedFile();
                	if (selectedFile.exists()) {
                	    // The file exists, proceed with reading it
                	    String path = selectedFile.getAbsolutePath();
                	    t1.setText(path);
                	    try {
                	        BufferedImage image = ImageIO.read(new File(path));
                	        img1.setIcon(Fonctionalite.ResizeImage(path, 120, 120));
                	    } catch (IOException ex) {
                	        ex.printStackTrace();
                	        // Handle the IOException (e.g., show an error message)
                	    }
                	} else {
                	    System.out.println("File does not exist: " + selectedFile.getAbsolutePath());
                	}
                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Selected");
                }
                repaint();
                validate();
            }
        });

        ba.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = t1.getText(); // Get the file path from the text field

                // Check if the file exists in the database (replace this condition with your logic)
                if (Fonctionalite.fileExistsInDatabase(filePath)) {
                    // If the file exists, update the UI with the information and photo
                    String[] userInfo = Fonctionalite.getUserInfo(filePath);
                    if (userInfo != null && userInfo.length >= 2) {
                        t2.setText(userInfo[0]); // Display user information in t2
                        img2.setIcon(Fonctionalite.ResizeImage(filePath, 120, 120)); // Display the photo in img2
                    } else {
                        System.out.println("Invalid user information in the database.");
                        // Handle this case, show an error message or take appropriate action.
                    }
                } else {
                    // If the file doesn't exist in the database, show a message
                    t2.setText("Person doesn't exist in the database.");
                    img2.setIcon(null); // Clear the img2 icon
                }

                // Clear the input file path
                t1.setText("");
            }
        });


            bh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Desktop d=Desktop.getDesktop();
				try {
					d.print(fc);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
            br.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String info = t2.getText();
                    try {
                        // Chargement du fichier JRXML
                        FileInputStream ff = new FileInputStream(new File("Images/policerapport.jrxml"));
                        JasperDesign jd = JRXmlLoader.load(ff);

                        // Compilation du rapport Jasper
                        JasperReport js = JasperCompileManager.compileReport(jd);

                        // Paramètres du rapport
                        Map<String, Object> params = new HashMap<>();
                        params.put("Parameter1", info);

                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        params.put("Parameter2", "Policier responsable: " + app.user + "\n" + dateFormat.format(date));

                        // Remplissage du rapport
                        JasperPrint jp = JasperFillManager.fillReport(js, params);

                        // Export du rapport en PDF
                        dateFormat = new SimpleDateFormat("dd MM yyyy HH mm ss");
                        File outputFile = new File("Images/" + dateFormat.format(date) + ".pdf");
                        FileOutputStream ff2 = new FileOutputStream(outputFile);
                        JasperExportManager.exportReportToPdfStream(jp, ff2);

                        // Fermeture des flux
                        ff.close();
                        ff2.flush();
                        ff2.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
	}
}
