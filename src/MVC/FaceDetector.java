package MVC;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class FaceDetector {

    private static Net faceRecognitionNet;

    static {
        // Set the absolute path to the OpenCV native libraries
        System.setProperty("java.library.path", "C:\\opencv\\build\\java\\x64\\opencv_java470.dll");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Load the pre-trained deep learning model for face recognition
        String protoPath = "Images/deploy.prototxt.txt";
     
        String modelPath = "Images/res10_300x300_ssd_iter_140000.caffemodel";
        faceRecognitionNet = Dnn.readNetFromCaffe(protoPath, modelPath);
    }

    public static int detectAndRecognizeFace(BufferedImage image1, BufferedImage image2) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Convert images to OpenCV Mat format
        Mat matImage1 = convertBufImg2Mat(image1);
        Mat matImage2 = convertBufImg2Mat(image2);

        // Detect faces in the images
        MatOfRect faceDetections1 = detectFaces(matImage1);
        MatOfRect faceDetections2 = detectFaces(matImage2);

        // Compare faces
        boolean samePerson = compareFaces(matImage1, faceDetections1, matImage2, faceDetections2);

        if (samePerson) {
            System.out.println("Same person: Faces are detected as the same person.");
        } else {
            System.out.println("Different persons: Faces are detected as different persons.");
        }

        return faceDetections1.toArray().length;
    }

    private static MatOfRect detectFaces(Mat image) {
        CascadeClassifier faceDetector = new CascadeClassifier("Images/haarcascade_frontalface_default.xml");
        if (!faceDetector.load("Images/haarcascade_frontalface_default.xml")) {
            return new MatOfRect();
        }

        System.out.print("Detecting faces - ");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        System.out.println(String.format(" %s faces detected", faceDetections.toArray().length));

        return faceDetections;
    }

    private static boolean compareFaces(Mat image1, MatOfRect faceDetections1, Mat image2, MatOfRect faceDetections2) {
        if (faceDetections1.toArray().length != 1 || faceDetections2.toArray().length != 1) {
            // If either image has more or fewer than one face, return false
            return false;
        }

        // Extract the faces from the images
        Rect rect1 = faceDetections1.toArray()[0];
        Rect rect2 = faceDetections2.toArray()[0];
        Mat face1 = new Mat(image1, rect1);
        Mat face2 = new Mat(image2, rect2);

        // Resize faces to match the input size of the face recognition model
        Imgproc.resize(face1, face1, new Size(300, 300));
        Imgproc.resize(face2, face2, new Size(300, 300));

        // Convert the faces to blobs
        Mat blob1 = Dnn.blobFromImage(face1, 1.0, new Size(300, 300), new Scalar(104, 177, 123), false, false);
        Mat blob2 = Dnn.blobFromImage(face2, 1.0, new Size(300, 300), new Scalar(104, 177, 123), false, false);

        // Pass the blobs through the face recognition network
        faceRecognitionNet.setInput(blob1);
        Mat embeddings1 = faceRecognitionNet.forward();

        faceRecognitionNet.setInput(blob2);
        Mat embeddings2 = faceRecognitionNet.forward();

        // Calculate the L2 distance between the embeddings
        double distance = Core.norm(embeddings1, embeddings2);

        // You may need to adjust this threshold based on your application
        double threshold = 0.8;

        // Return true if the distance is below the threshold
        return distance < threshold;
    }

    private static Mat convertBufImg2Mat(BufferedImage image) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            byte[] pixels = byteArrayOutputStream.toByteArray();

            return Imgcodecs.imdecode(new MatOfByte(pixels), Imgcodecs.IMREAD_UNCHANGED);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        // Load the images for face detection and recognition
        BufferedImage image1 = ImageIO.read(new File("Images/12.jpg"));
        BufferedImage image2 = ImageIO.read(new File("Images/khouloud.jpg"));

        // Detect and recognize faces in the images
        int numDetectedFaces = detectAndRecognizeFace(image1, image2);
        System.out.println("Number of faces detected: " + numDetectedFaces);
    }
}
