package net.test;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.lang.reflect.Field;

public class Main {
//    static {
//        // Load the native OpenCV library
//        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
////        System.load("C:\\opencv\\build\\java\\x64\\opencv_java470.dll");
//    }

    public static Mat detectFace(Mat inputImage) {
        MatOfRect facesDetected = new MatOfRect();
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        int minFaceSize = Math.round(inputImage.rows() * 0.1f);
        cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
        cascadeClassifier.detectMultiScale(inputImage,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );
        Rect[] facesArray =  facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(inputImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3 );
        }
        return inputImage;
    }

    public static void main(String[] args) {
//
//        String nativeLibraryPath = "C:\\opencv\\build\\java\\x64";
//        try {
//            // Get the original java.library.path
//            String originalLibraryPath = System.getProperty("java.library.path");
//
//            // Append the new library path
//            StringBuilder newLibraryPath = new StringBuilder(originalLibraryPath);
//            newLibraryPath.append(File.pathSeparator).append(nativeLibraryPath);
//
//            // Set the new java.library.path
//            System.setProperty("java.library.path", newLibraryPath.toString());
//
//            // Trigger the JVM to re-read the updated java.library.path
//            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
//            fieldSysPath.setAccessible(true);
//            fieldSysPath.set(null, null);
//        } catch (Exception e) {
//            // Handle exceptions if any
//            e.printStackTrace();
//        }


        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        // Register the default camera
        VideoCapture cap = new VideoCapture(0);

        // Check if video capturing is enabled
        if (!cap.isOpened()) {
            System.exit(-1);
        }

        // Matrix for storing image
        Mat image = new Mat();
        // Frame for displaying image
        MyFrame frame = new MyFrame();
        frame.setVisible(true);

        // Main loop
        while (true) {
            // Read current camera frame into matrix
            cap.read(image);
            // Render frame if the camera is still acquiring images
            if (image != null) {
                frame.render(image);
            } else {
                System.out.println("No captured frame -- camera disconnected");
                break;
            }
        }

    }
}
