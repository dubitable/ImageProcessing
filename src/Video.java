import org.bytedeco.javacv.*;
import java.io.File;
public class Video {
    FFmpegFrameGrabber grabber;
    
    public Video(String filename) throws Exception{
        grabber = new FFmpegFrameGrabber(filename);
    }

    public void saveAllFrames(String dir) throws Exception{
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
}  
