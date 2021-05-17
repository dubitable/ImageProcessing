import java.io.IOException;

public class HackImageTester {
    static String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
    static String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

    public static void test4() throws IOException{

        HackImage.setDir("images/hackathon");
        HackImage anatole = new HackImage(Image.open(img2));

        int[][][][] sections = anatole.sectionCol(25);
        HackImage[] results = HackImage.seperateCol(sections);

        results[0].saveAs("why1.jpg");
        results[1].saveAs("why2.jpg");
    }
}
