import org.bytedeco.javacv.*;
import java.io.File;
public class Video {
    private FFmpegFrameGrabber grabber;
    private static String dir = "video";
    
    public Video(String filename) throws Exception{
        grabber = new FFmpegFrameGrabber(filename);
    }

    public static void setDir(String directory){
        dir = directory;
    }

    public void saveAllFrames() throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            frame.saveAs(i+".jpg");
        }
        grabber.stop();
    } 

    public void rotate() throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            Image rotated = frame.applyTransformation(Image.rotation((Math.PI * 2 / n) * i));
            rotated.resize(frame.height(), frame.width()).saveAs(i+".jpg");
        }
        grabber.stop();
    } 
    public void applyTransformation(double[][] matrix) throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            Image rotated = frame.applyTransformation(matrix);
            rotated.resize(frame.height(), frame.width()).saveAs(i+".jpg");
        }
        grabber.stop();
    } 
    public void applyConvolution(double[][] matrix, int iterations) throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            Image rotated = frame.applyConvolution(matrix, iterations);
            rotated.resize(frame.height(), frame.width()).saveAs(i+".jpg");
        }
        grabber.stop();
    } 
    public void applyConvolution(double[][] matrix) throws Exception{
        applyConvolution(matrix, 1);
    }
    public void applyRandomConvolution() throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            Image rotated = frame.applyConvolution(Image.randomConvolution());
            rotated.resize(frame.height(), frame.width()).saveAs(i+".jpg");
        }
        grabber.stop();
    } 
    public void applyRandomTransformation() throws Exception{
        File file = new File("images/videos/"+dir);
        file.mkdir();
        Image.setDir("images/videos/"+dir);
        grabber.start();
        int n = grabber.getLengthInFrames();
        for (int i = 0 ; i <  n; i++) {
            Image frame = Image.open(grabber.grab().getBufferedImage());
            Image rotated = frame.applyTransformation(Image.randomConvolution());
            rotated.resize(frame.height(), frame.width()).saveAs(i+".jpg");
        }
        grabber.stop();
    } 
}  
