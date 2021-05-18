import java.io.IOException;

public class HackImageTester {
    static String img1 = "/Users/pierrequereuil/Desktop/head.jpeg";
    static String img2 = "/Users/pierrequereuil/Desktop/anatole.png";

    public static void test4() throws IOException{

        HackImage.setDir("images/hackathon");
        HackImage head = new HackImage(Image.open(img1));
        HackImage anatole = new HackImage(Image.open(img2));

        HackImage.joinCol(head, anatole, 10).saveAs("whydididothis.jpg");
    }
}
