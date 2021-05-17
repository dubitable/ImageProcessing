import java.io.IOException;

public class HackImageTester {
    static String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
    static String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

    public static void test4() throws IOException{

        HackImage.setDir("images/hackathon");
        HackImage anatole = new HackImage(Image.open(img2));
        HackImage[] results = anatole.seperateRow(anatole.sectionRow(5));
        results[0].saveAs("results1.jpg");
        results[1].saveAs("results2.jpg");
    }
}
