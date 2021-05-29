import java.io.IOException;
import org.bytedeco.javacv.FrameGrabber;

public class VideoTester {
    public static void test() throws IOException, Exception, FrameGrabber.Exception{
        String filename = "/Users/pierrequereuil/Desktop/ahhhh.mp4";
        Video.setDir("video1");
        Video video = new Video(filename);
        video.saveAllFrames();
    }
    public static void rotate() throws Exception{
        String filename = "/Users/pierrequereuil/Desktop/ahhhh.mp4";
        Video video = new Video(filename);
        Video.setDir("video2");
        video.rotate();
    }
    public static void edges() throws Exception{
        String filename = "/Users/pierrequereuil/Desktop/ahhhh.mp4";
        Video video = new Video(filename);
        Video.setDir("video3");
        video.applyConvolution(Image.sharpen, 5);
    }
}
