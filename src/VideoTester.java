import java.io.IOException;
import org.bytedeco.javacv.FrameGrabber;

public class VideoTester {
    public static void test() throws IOException, Exception, FrameGrabber.Exception{
        String filename = "/Users/pierrequereuil/Desktop/ahhhh.mp4";
        Video video = new Video(filename);
        video.saveAllFrames("video1");
    }
}
