package MVC;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class Fonctionalite {
	 public static ImageIcon matToImageIcon(Mat mat, int width, int height) {
	        MatOfByte matOfByte = new MatOfByte();
	        Imgcodecs.imencode(".png", mat, matOfByte);

	        byte[] byteArray = matOfByte.toArray();
	        ImageIcon icon = new ImageIcon(byteArray);
	        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

	        return new ImageIcon(image);
	    }

    public static ImageIcon ResizeImage(String ImagePath, int x, int y) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    public static BufferedImage ResizeImage2(String ImagePath, int x, int y) {
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Image newImg = img.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        BufferedImage image = (BufferedImage) newImg;
        return image;
    }

    public static boolean fileExistsInDatabase(String filePath) {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT COUNT(*) FROM personne WHERE pathorigin='" + filePath + "';";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            conn.close();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get user information from the database
    public static String[] getUserInfo(String filePath) {
        Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
            java.sql.Statement st = conn.createStatement();
            String query = "SELECT * FROM personne WHERE pathorigin='" + filePath + "';";
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String[] userInfo = new String[2];
                userInfo[0] = "Nom: " + rs.getString(1) + ' ' + rs.getString(2) + "\nC.I.N: " + rs.getString(3)
                        + "\nDate de naissance:\n " + rs.getString(4) + "\nOccupation: " + rs.getString(5)
                        + "\nRecherché? " + rs.getString(6);
                userInfo[1] = rs.getString(7);
                rs.close();
                conn.close();
                return userInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static String[] connecter(String pp) {
        Connection conn; // Declare Connection as a class variable

        String[] a = new String[2];
        try {
            // connexion
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
            java.sql.Statement st = conn.createStatement();
            String query = "Select * from personne where pathorigin='" + pp + "' ;";
            ResultSet rs = st.executeQuery(query);
            // organization des donn�es
            if (rs.next()) {
                a[0] = "Nom: " + rs.getString(1) + ' ' + rs.getString(2) + "\nC.I.N: " + rs.getString(3)
                        + "\nDate de naissance:\n " + rs.getString(4) + "\nOccupation: " + rs.getString(5)
                        + "\nRecherch�? " + rs.getString(6);
                a[1] = rs.getString(7);
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            // to see problems
            e.printStackTrace();
        }
        return a;
    }

    static String[] connecterA() {
        Connection conn; // Declare Connection as a class variable

        String[] a = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/isimm", "root", "");
            java.sql.Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "Select pathorigin from personne;";
            ResultSet rs = st.executeQuery(query);

            // Move the cursor to the last row to get the row count
            rs.last();
            int rowCount = rs.getRow();

            // Move the cursor back to before the first row
            rs.beforeFirst();

            a = new String[rowCount];
            int j = 0;
            while (rs.next()) {
                a[j] = rs.getString(1);
                j++;
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static String recherimg() {
        String[] b = connecterA();
        double min = 100;
        int i = 0;
        int indx = 0;
        while (i < b.length) {
            double j = compare(b[i]);
            if (min > j) {
                min = j;
                indx = i;
            }

            i++;
        }

        return (b[indx]);
    }

    public static double compare(String imagePath) {
        BufferedImage img1 = null;
        BufferedImage img2 = null;
        try {
        	
            File file1 = new File(cherche.t1.getText());
            File file2 = new File(imagePath);
            
            img1 = ImageIO.read(file1);
            img2 = ImageIO.read(file2);
        } catch (IOException e) {
            e.printStackTrace();
            return -1.0; // Indicate an error
        }

        if (img1 == null || img2 == null) {
            System.err.println("Error: One or both images couldn't be loaded.");
            return -1.0; // Indicate an error
        }

        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();

        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            return -1.0; // Indicate an error
        }

        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >> 8) & 0xff;
                int b1 = (rgb1) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >> 8) & 0xff;
                int b2 = (rgb2) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }

        double n = width1 * height1 * 3.0;
        double p = diff / n / 255.0;
        return p * 100.0;
    }


}
