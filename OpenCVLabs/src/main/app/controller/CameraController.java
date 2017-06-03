package main.app.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.app.Utils;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static main.app.Utils.mat2Image;

public class CameraController {

    private ImageView histogram;
    private Label nPeople;
    private Label dPeople;

    private boolean isGray = false;
    private boolean isLogo = false;
    private boolean isHistogram = false;
    private boolean isDetect = false;

    private Mat logo = Highgui.imread("src/main/resources/images/logo.png");

    private ScheduledExecutorService timer;
    private VideoCapture capture = new VideoCapture();
    private boolean cameraActive = false;

    private CascadeClassifier faceCascade = new CascadeClassifier();
    private int absoluteFaceSize = 0;

    public CameraController(ImageView histogram, Label nPeople, Label dPeople) {
        this.histogram = histogram;
        this.nPeople = nPeople;
        this.dPeople = dPeople;
    }

    public void initializeFaceDetection() {
        faceCascade.load("src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
    }

    public void startCamera(ImageView currentFrame) {
        if (!this.cameraActive) {
            int cameraId = 0;
            this.capture.open(cameraId);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = () -> {
                    // effectively grab and process a single frame
                    Mat frame = grabFrame();
                    // convert and show the frame
                    Image imageToShow = mat2Image(frame);
                    updateImageView(currentFrame, imageToShow);
                };
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
            } else {
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            this.cameraActive = false;
            this.stopAcquisition();
        }
    }

    private Mat grabFrame()
    {
        Mat frame = new Mat();

        if (this.capture.isOpened())
        {
            try
            {
                this.capture.read(frame);

                if (!frame.empty())
                {
                    if (isGray) {
                        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                    }
                    if (isLogo) {
                        Rect roi = new Rect(frame.cols() - logo.cols(), frame.rows() - logo.rows(), logo.cols(),
                                logo.rows());
                        Mat imageROI = frame.submat(roi);
                        // add the logo: method #1
                        Core.addWeighted(imageROI, 1.0, logo, 0.8, 0.0, imageROI);

                        // add the logo: method #2
                        //logo.copyTo(imageROI, logo);
                    }
                    if (isHistogram) {
                        this.showHistogram(frame, isGray);
                    }
                    if (isDetect) {
                        this.detectAndDisplay(frame);
                    }
                }

            }
            catch (Exception e)
            {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private void detectAndDisplay(Mat frame) {
        MatOfRect faces = new MatOfRect();
        Mat grayFrame = new Mat();

        // convert the frame in gray scale
        Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
        // equalize the frame histogram to improve the result
        Imgproc.equalizeHist(grayFrame, grayFrame);

        // compute minimum face size (20% of the frame height, in our case)
        if (this.absoluteFaceSize == 0)
        {
            int height = grayFrame.rows();
            if (Math.round(height * 0.2f) > 0)
            {
                this.absoluteFaceSize = Math.round(height * 0.2f);
            }
        }

        // detect faces
        this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2,
                0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(
                this.absoluteFaceSize, this.absoluteFaceSize), new Size());
        Rect[] facesArray = faces.toArray();
        Platform.runLater(() -> nPeople.setText(String.valueOf(facesArray.length)));
        String distances = "";
        for (int i = 0; i < facesArray.length; i++) {
            Core.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0, 255), 3);
            distances += i + ": " + calculateDistance(facesArray[i].br(), facesArray[i].tl()) + " sm\n";
        }
        String finalDistances = distances;
        Platform.runLater(() -> dPeople.setText(finalDistances));
    }

    private long calculateDistance(Point right, Point left) {
        double realObject = 250.0;
        double focalLength = 35;
        double k = 0.48;
        return Math.round(realObject*focalLength/((right.x - left.x)*k));
    }
    /**
     * Compute and show the histogram for the given {@link Mat} image
     *  @param frame
     *            the {@link Mat} image for which compute the histogram
     * @param gray
     */
    private void showHistogram(Mat frame, boolean gray)
    {
        // split the frames in multiple images
        List<Mat> images = new ArrayList<Mat>();
        Core.split(frame, images);

        // set the number of bins at 256
        MatOfInt histSize = new MatOfInt(256);
        // only one channel
        MatOfInt channels = new MatOfInt(0);
        // set the ranges
        MatOfFloat histRange = new MatOfFloat(0, 256);

        // compute the histograms for the B, G and R components
        Mat hist_b = new Mat();
        Mat hist_g = new Mat();
        Mat hist_r = new Mat();

        // B component or gray image
        Imgproc.calcHist(images.subList(0, 1), channels, new Mat(), hist_b, histSize, histRange, false);

        // G and R components (if the image is not in gray scale)
        if (!gray)
        {
            Imgproc.calcHist(images.subList(1, 2), channels, new Mat(), hist_g, histSize, histRange, false);
            Imgproc.calcHist(images.subList(2, 3), channels, new Mat(), hist_r, histSize, histRange, false);
        }

        // draw the histogram
        int hist_w = 150; // width of the histogram image
        int hist_h = 150; // height of the histogram image
        int bin_w = (int) Math.round(hist_w / histSize.get(0, 0)[0]);

        Mat histImage = new Mat(hist_h, hist_w, CvType.CV_8UC3, new Scalar(0, 0, 0));
        // normalize the result to [0, histImage.rows()]
        Core.normalize(hist_b, hist_b, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());

        // for G and R components
        if (!gray)
        {
            Core.normalize(hist_g, hist_g, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
            Core.normalize(hist_r, hist_r, 0, histImage.rows(), Core.NORM_MINMAX, -1, new Mat());
        }

        // effectively draw the histogram(s)
        for (int i = 1; i < histSize.get(0, 0)[0]; i++)
        {
            // B component or gray image
            Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_b.get(i - 1, 0)[0])), new Point(
                    bin_w * (i), hist_h - Math.round(hist_b.get(i, 0)[0])), new Scalar(255, 0, 0), 2, 8, 0);
            // G and R components (if the image is not in gray scale)
            if (!gray)
            {
                Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_g.get(i - 1, 0)[0])),
                        new Point(bin_w * (i), hist_h - Math.round(hist_g.get(i, 0)[0])), new Scalar(0, 255, 0), 2, 8,
                        0);
                Core.line(histImage, new Point(bin_w * (i - 1), hist_h - Math.round(hist_r.get(i - 1, 0)[0])),
                        new Point(bin_w * (i), hist_h - Math.round(hist_r.get(i, 0)[0])), new Scalar(0, 0, 255), 2, 8,
                        0);
            }
        }

        // display the whole, on the FX thread
        final Image histImg = mat2Image(histImage);

        Platform.runLater(() -> histogram.setImage(histImg));

    }

    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            //this.capture.release();
        }
    }

    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    protected void setClosed()
    {
        this.stopAcquisition();
    }

    public void setIsGray(boolean isGray) {
        this.isGray = isGray;
    }

    public void setLogo(boolean isLogo) {
        this.isLogo = isLogo;
    }

    public void setHistogram(boolean isHistogram) {
        this.isHistogram = isHistogram;
    }

    public void setDetect(boolean detect) {
        isDetect = detect;
    }

    public void takeScreenShot() {
        Mat frame = new Mat();
        this.capture.read(frame);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Dimension d = new Dimension();
        d.height = 10;
        d.width = 10;

        assert robot != null;
        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(d));

        BufferedImage bgrScreenshot = new BufferedImage(screenShot.getWidth(),
                screenShot.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        boolean done = bgrScreenshot.getGraphics().drawImage(screenShot, 0, 0, null);

        byte[] imageBytes = ((DataBufferByte) bgrScreenshot.getRaster().getDataBuffer())
                .getData();
        Mat outputFrame = new Mat(frame.size(), CvType.CV_8UC3);
        outputFrame.put(0, 0, imageBytes);
        String filename = new Date().toString() + ".png";
        Highgui.imwrite(filename, outputFrame);
        ImageIcon image = new ImageIcon(filename);
    }
}
